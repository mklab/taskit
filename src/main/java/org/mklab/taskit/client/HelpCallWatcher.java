/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.activity.HelpCallObserver;

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class HelpCallWatcher {

  private static final int WATCH_PERIOD = 60 * 1000;
  private Timer helpCallWatchTimer;
  private ClientFactory clientFactory;
  private int latestHelpCallCount;
  private HelpCallObserver helpCallObserver;
  private boolean running = false;

  /**
   * {@link HelpCallWatcher}オブジェクトを構築します。
   */
  HelpCallWatcher(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
    this.helpCallWatchTimer = new Timer() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void run() {
        fetchHelpCallCount();
      }
    };
  }

  /**
   * 監視を開始します。
   * <p>
   * 複数回呼び出しても、多重に監視が働いてしまうことはありません。
   */
  public void start() {
    if (isRunning()) return;

    fetchHelpCallCount();
    this.helpCallWatchTimer.scheduleRepeating(WATCH_PERIOD);
    this.running = true;
  }

  /**
   * 監視を終了します。
   */
  public void stop() {
    if (isRunning() == false) return;
    this.helpCallWatchTimer.cancel();
  }

  private boolean isRunning() {
    return this.running;
  }

  /**
   * 最後に取得したヘルプコール数を取得します。
   * 
   * @return ヘルプコール数
   */
  public int getHelpCallCount() {
    return this.latestHelpCallCount;
  }

  /**
   * 今ヘルプコール数を取得するようリクエストします。
   * <p>
   * 取得完了までブロックすることはありません。
   */
  public void watchNow() {
    fetchHelpCallCount();
  }

  /**
   * ヘルプコールの監視オブジェクトを設定します。
   * 
   * @param observer 監視オブジェクト
   */
  public void setHelpCallObserver(HelpCallObserver observer) {
    this.helpCallObserver = observer;
  }

  void fireHelpCallCountChanged(int count) {
    if (this.helpCallObserver != null && this.latestHelpCallCount != count) this.helpCallObserver.helpCallCountChanged(count);
    this.latestHelpCallCount = count;
  }

  private void fetchHelpCallCount() {
    this.clientFactory.getRequestFactory().helpCallRequest().getHelpCallCount().fire(new Receiver<Long>() {

      @Override
      public void onSuccess(Long response) {
        fireHelpCallCountChanged(response.intValue());
      }
    });
  }
}
