/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.LocalDatabase.Query;
import org.mklab.taskit.client.activity.HelpCallObserver;
import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.TaskitRequestFactory;
import org.mklab.taskit.shared.event.HelpCallEvent;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.media.client.Audio;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;


/*
 * TODO パッケージの移動 
 * TODO Server Push
 */
/**
 * ヘルプコールを監視するクラスです。
 * <p>
 * ヘルプコールの監視は、通信量を減らすため件数のみ行います。<br>
 * その他ヘルプコールの詳細を取得にもこのクラスを用いることにより、件数の変更を検出し{@link HelpCallObserver}
 * に通知することが出来ます。
 * 
 * @author Yuhi Ishikura
 */
public class HelpCallWatcher {

  private HelpCallObserver helpCallObserver;
  private boolean running = false;
  private LocalDatabase database;
  private int lastHelpCallCount = 0;
  private RemoteEventService remoteEventService;

  private Query<Integer> helpCallCountQuery = new Query<Integer>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<Integer> receiver) {
      requestFactory.helpCallRequest().getHelpCallCount().fire(receiver);
    }
  };

  /**
   * {@link HelpCallWatcher}オブジェクトを構築します。
   */
  HelpCallWatcher(LocalDatabase database, RemoteEventService remoteEventService) {
    if (database == null || remoteEventService == null) throw new NullPointerException();
    this.database = database;
    this.remoteEventService = remoteEventService;
  }

  /**
   * 監視を開始します。
   * <p>
   * 複数回呼び出しても、多重に監視が働いてしまうことはありません。
   */
  public void start() {
    if (isRunning()) return;

    this.remoteEventService.addListener(HelpCallEvent.DOMAIN, new RemoteEventListener() {

      @Override
      public void apply(Event anEvent) {
        if (anEvent instanceof HelpCallEvent == false) return;

        fireHelpCallCountChanged(((HelpCallEvent)anEvent).getHelpCallCount());
      }
    });

    fetchHelpCallCount();
    this.running = true;
  }

  /**
   * 監視を終了します。
   */
  public void stop() {
    if (isRunning() == false) return;
    this.remoteEventService.removeListeners(HelpCallEvent.DOMAIN);
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
   * 呼び出し一覧を取得します。
   * 
   * @param receiver 結果を受け取るレシーバー。結果は、キャッシュが存在する場合は、そのキャッシュと新たに取得したものの二回渡されます。
   */
  public void getHelpCallList(final Receiver<List<HelpCallProxy>> receiver) {
    this.database.getCacheAndExecute(LocalDatabase.CALL_LIST, new Receiver<List<HelpCallProxy>>() {

      @Override
      public void onSuccess(List<HelpCallProxy> response) {
        receiver.onSuccess(response);
        fireHelpCallCountChanged(response.size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        receiver.onFailure(error);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        receiver.onConstraintViolation(violations);
      }
    });
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
    this.database.execute(this.helpCallCountQuery, new Receiver<Integer>() {

      @Override
      public void onSuccess(Integer response) {
        fireHelpCallCountChanged(response.intValue());
      }
    });
  }
}
