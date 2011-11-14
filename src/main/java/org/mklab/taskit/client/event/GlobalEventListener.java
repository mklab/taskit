/**
 * 
 */
package org.mklab.taskit.client.event;

import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.event.Domains;

import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;


/**
 * アプリケーション全体で必要なイベント監視を行うクラスです。
 * <p>
 * イベントを処理するリスナの実装を、デコレーターパターンで切り替える機能を提供します。
 * 
 * @author Yuhi Ishikura
 */
public class GlobalEventListener {

  private RemoteEventService eventService;
  @SuppressWarnings("rawtypes")
  private RemoteEventListenerDecorator globalListener;
  private boolean running = false;

  /**
   * {@link GlobalEventListener}オブジェクトを構築します。
   */
  public GlobalEventListener() {
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
        this.globalListener = new StudentRemoteEventListenerDecorator();
        domain = Domains.STUDENT;
        break;
      case TA:
        this.globalListener = new TaRemoteEventListenerDecorator();
        domain = Domains.TA;
        break;
      case TEACHER:
        this.globalListener = new TeacherRemoteEventListenerDecorator();
        domain = Domains.TEACHER;
        break;
      default:
        throw new UnsupportedOperationException();
    }
    this.eventService.addListener(domain, this.globalListener);
    this.running = true;
  }

  /**
   * アクティビティ間で共通に利用するグローバルリスナを取得します。
   * 
   * @return グローバルリスナ
   */
  @SuppressWarnings("rawtypes")
  public RemoteEventListenerDecorator getGlobalListener() {
    if (isListening() == false) throw new IllegalStateException("system not started."); //$NON-NLS-1$
    return this.globalListener;
  }

  /**
   * アプリケーションを停止します。
   */
  public void unlisten() {
    if (this.running == false) throw new IllegalStateException();
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
