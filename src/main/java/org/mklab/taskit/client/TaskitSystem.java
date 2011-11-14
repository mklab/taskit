/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.event.RemoteEventListenerDecorator;
import org.mklab.taskit.client.event.StudentRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TaRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TeacherRemoteEventListenerDecorator;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.event.Domains;

import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;


/**
 * @author Yuhi Ishikura
 */
public class TaskitSystem {

  private RemoteEventService eventService;
  @SuppressWarnings("rawtypes")
  private RemoteEventListenerDecorator globalListener;
  private boolean running = false;

  /**
   * {@link TaskitSystem}オブジェクトを構築します。
   */
  public TaskitSystem() {
    this.eventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
  }

  /**
   * アプリケーションを開始します。
   * 
   * @param user ログインユーザー
   */
  public void start(UserProxy user) {
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
    return this.globalListener;
  }

  /**
   * アプリケーションを停止します。
   */
  public void stop() {
    if (this.running == false) throw new IllegalStateException();
    this.eventService.removeListeners();
  }

  /**
   * システムが実行中か調べます。
   * 
   * @return 実行中かどうか
   */
  public boolean isRunning() {
    return this.running;
  }

}
