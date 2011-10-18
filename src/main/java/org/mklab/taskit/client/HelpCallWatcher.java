/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.LocalDatabase.Query;
import org.mklab.taskit.client.activity.HelpCallObserver;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class HelpCallWatcher {

  private static final int WATCH_PERIOD = 60 * 1000;
  private Timer helpCallWatchTimer;
  private HelpCallObserver helpCallObserver;
  private boolean running = false;
  private LocalDatabase database;
  private int lastHelpCallCount = 0;

  private Query<Long> helpCallCountQuery = new Query<Long>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<Long> receiver) {
      requestFactory.helpCallRequest().getHelpCallCount().fire(receiver);
    }
  };

  /**
   * {@link HelpCallWatcher}オブジェクトを構築します。
   */
  HelpCallWatcher(LocalDatabase database) {
    this.database = database;
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
    return this.lastHelpCallCount;
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
    if (this.helpCallObserver != null && this.lastHelpCallCount != count) {
      this.helpCallObserver.helpCallCountChanged(count);

      // TAへの通知。オブザーバーに記述したいけれどもアクティビティの切り替えの度に音がなることになってしまう
      if (this.lastHelpCallCount == 0 && count > 0) {
        playCallSound();
      }
    }
    this.lastHelpCallCount = count;
  }

  private static void playCallSound() {
    final Audio audio = Audio.createIfSupported();
    if (audio != null) {
      audio.setSrc("taskit/call.mp3"); //$NON-NLS-1$
      audio.play();
    }
  }

  private void fetchHelpCallCount() {
    this.database.execute(this.helpCallCountQuery, new Receiver<Long>() {

      @Override
      public void onSuccess(Long response) {
        fireHelpCallCountChanged(response.intValue());
      }
    });
  }
}
