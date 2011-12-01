/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.event;

import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.event.Domains;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEvent;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEventListener;
import de.novanic.eventservice.client.event.listener.unlisten.UnlistenEventListenerAdapter;


/**
 * アプリケーション全体で必要なイベント監視を行うクラスです。
 * <p>
 * イベントを処理するリスナの実装を、デコレーターパターンで切り替える機能を提供します。
 * 
 * @author Yuhi Ishikura
 */
public class GlobalEventListener {

  private RemoteEventService eventService;
  private RemoteEventListenerDecorator globalListener;
  private RemoteEventListener baseListener;
  private int timeoutRecoveryFailureCount = 0;
  /** ログインユーザーです。 */
  private UserProxy loginUser;
  private boolean running = false;

  /**
   * {@link GlobalEventListener}オブジェクトを構築します。
   * 
   * @param baseListener どのリスナーよりも先にイベントを監視するリスナー
   */
  public GlobalEventListener(RemoteEventListener baseListener) {
    this.baseListener = baseListener;
    this.eventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
  }

  /**
   * アプリケーションを開始します。
   * 
   * @param user ログインユーザー
   */
  public void listenWith(UserProxy user) {
    if (this.running) throw new IllegalStateException();
    this.loginUser = user;
    relisten();
  }

  private void relisten() {
    final Domain domain;
    switch (this.loginUser.getType()) {
      case STUDENT:
        domain = Domains.STUDENT;
        break;
      case TA:
        domain = Domains.TA;
        break;
      case TEACHER:
        domain = Domains.TEACHER;
        break;
      default:
        throw new UnsupportedOperationException();
    }

    this.globalListener = new RemoteEventListenerDecorator();
    this.eventService.addUnlistenListener(UnlistenEventListener.Scope.LOCAL, new UnlistenEventListenerAdapter() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onUnlisten(@SuppressWarnings("unused") UnlistenEvent anUnlistenEvent) {
        if (isListening()) {
          timeoutDetected();
        }
      }
    }, new AsyncCallback<Void>() {

      @Override
      public void onFailure(@SuppressWarnings("unused") Throwable caught) {
        // do nothing
      }

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onSuccess(@SuppressWarnings("unused") Void result) {
        timeoutRecoveryFailureCount = 0;
      }
    });
    this.eventService.addListener(domain, new RemoteEventListener() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void apply(Event anEvent) {
        GlobalEventListener.this.baseListener.apply(anEvent);
        GlobalEventListener.this.globalListener.apply(anEvent);
      }
    });
    this.running = true;
  }

  void timeoutDetected() {
    // 復帰に失敗し続けると試みるのをやめる
    if (this.timeoutRecoveryFailureCount++ > 5) {
      return;
    }

    // 復帰を試みる
    if (recoverFromTimeout()) {
      relisten();
      return;
    }

    // 復帰出来なければページをリロード
    Window.alert("Detected connection timeout."); //$NON-NLS-1$
    reload();
  }

  /**
   * アプリケーションを停止します。
   */
  public void unlisten() {
    if (this.running == false) throw new IllegalStateException();
    this.running = false;
    this.eventService.removeListeners();
  }

  /**
   * タイムアウトからの復帰を試みます。
   * 
   * @return 復帰できたらtrue,できなければfalse
   */
  private boolean recoverFromTimeout() {
    if (this.globalListener.getListener() instanceof TimeoutRecoverable) {
      final TimeoutRecoverable recovery = (TimeoutRecoverable)this.globalListener.getListener();
      try {
        recovery.recoverFromTimeout();
        return true;
      } catch (TimeoutRecoveryFailureException e) {
        return false;
      }
    }

    return false;
  }

  /**
   * ブラウザの再読み込みを行います。
   */
  native void reload() /*-{ 
                              $wnd.location.reload(); 
                              }-*/;

  /**
   * リスナの実装を切り替えます。
   * 
   * @param listener リスナの実装
   */
  public void setImplementation(RemoteEventListener listener) {
    if (listener == null) throw new NullPointerException();
    this.globalListener.setListener(listener);
  }

  /**
   * システムが実行中か調べます。
   * 
   * @return 実行中かどうか
   */
  public boolean isListening() {
    return this.running;
  }

  /**
   * 何も行わない非同期コールバックです。
   * 
   * @author Yuhi Ishikura
   */
  static final class VoidAsyncCallback implements AsyncCallback<Void> {

    @Override
    public void onSuccess(@SuppressWarnings("unused") Void result) {
      // never called.
    }

    @Override
    public void onFailure(@SuppressWarnings("unused") Throwable caught) {
      // never called.
    }
  }

}
