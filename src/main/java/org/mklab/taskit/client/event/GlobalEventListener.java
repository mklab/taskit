/**
 * 
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
    final Domain domain;
    switch (user.getType()) {
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
          Window.alert("Detected connection timeout."); //$NON-NLS-1$
          reload();
        }
      }
    }, new AsyncCallback<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void result) {
        // never called.
      }

      @Override
      public void onFailure(@SuppressWarnings("unused") Throwable caught) {
        // never called.
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

  /**
   * ブラウザの再読み込みを行います。
   */
  public native void reload() /*-{ 
                              $wnd.location.reload(); 
                              }-*/;

  /**
   * アクティビティ間で共通に利用するグローバルリスナを取得します。
   * 
   * @return グローバルリスナ
   */
  public RemoteEventListenerDecorator getGlobalListener() {
    if (isListening() == false) throw new IllegalStateException("system not started."); //$NON-NLS-1$
    return this.globalListener;
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
   * システムが実行中か調べます。
   * 
   * @return 実行中かどうか
   */
  public boolean isListening() {
    return this.running;
  }

}
