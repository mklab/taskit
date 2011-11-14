/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.activity.TaskitActivity;
import org.mklab.taskit.client.event.GlobalEventListener;
import org.mklab.taskit.client.ui.HelpCallDisplayable;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.shared.event.HelpCallEvent;

import com.google.web.bindery.requestfactory.shared.Receiver;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;


/**
 * アプリケーションを駆動するシステムを取得します。
 * <p>
 * アプリケーション全体で共通な設定、イベント処理を行います。
 * 
 * @author Yuhi Ishikura
 */
public final class TaskitSystem implements ActivityObserver, RemoteEventListener {

  private ClientFactory clientFactory;
  private int lastHelpCallCount;

  /**
   * {@link TaskitSystem}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public TaskitSystem(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onActivityStart(@SuppressWarnings("unused") TaskitActivity activity) {
    // currently nothing to do.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(Event anEvent) {
    if (anEvent instanceof HelpCallEvent) {
      this.lastHelpCallCount = ((HelpCallEvent)anEvent).getHelpCallCount();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onActivityViewShown(@SuppressWarnings("unused") TaskitActivity activity, final TaskitView view, UserProxy user) {
    // ログイン後、初めて表示されるアクティビティの場合にのみ、リモートイベントの監視を開始する。
    final GlobalEventListener globalEventListener = this.clientFactory.getGlobalEventListener();
    if (globalEventListener.isListening() == false) {
      globalEventListener.listenWith(user);
      if (view instanceof HelpCallDisplayable && (user.getType() == UserType.TA || user.getType() == UserType.TEACHER)) {
        updateHelpCallCountAsync(view);
      }
    }

    // ユーザーによってヘルプコール数の通知領域の表示・非表示切り替え
    if (view instanceof HelpCallDisplayable) {
      final HelpCallDisplayable helpCallDisplay = (HelpCallDisplayable)view;
      final boolean isDisplayable = user.getType() != UserType.STUDENT;
      helpCallDisplay.setHelpCallDisplayEnabled(isDisplayable);
      if (isDisplayable) {
        helpCallDisplay.showHelpCallCount(this.lastHelpCallCount);
      }
    }
  }

  private void updateHelpCallCountAsync(final TaskitView view) {
    this.clientFactory.getRequestFactory().helpCallRequest().getHelpCallCount().fire(new Receiver<Integer>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void onSuccess(Integer response) {
        ((HelpCallDisplayable)view).showHelpCallCount(response.intValue());
        lastHelpCallCount = response.intValue();
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onActivityStop(@SuppressWarnings("unused") TaskitActivity activity) {
    // currently nothing to do.
  }

}
