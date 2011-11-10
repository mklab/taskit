/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * 学生の成績の統計を行うクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class RecordStatistics {

  /*
   * TODO 適切なロック
   * 今はとりあえず全部synchronizedにすることで並列対応している
   */

  Map<String, Double> submissionScoreMap;
  int maximumScore;
  Timer updateTimer;
  TimerTask computeTask;

  /**
   * {@link RecordStatistics}オブジェクトを構築します。
   */
  public RecordStatistics() {
    this.submissionScoreMap = new HashMap<String, Double>();
    this.updateTimer = new Timer("TASKit Statistics Updater"); //$NON-NLS-1$
  }

  /**
   * 統計の自動計算を開始します。
   */
  public void start() {
    if (isRunning()) return;

    this.computeTask = new TimerTask() {

      @Override
      public void run() {
        recompute();
      }
    };

    long interval = TimeUnit.MINUTES.toMillis(10);
    // 今すぐには計算しない。intervalミリ秒後から自動計算開始
    this.updateTimer.scheduleAtFixedRate(this.computeTask, interval, interval);
  }

  /**
   * 自動計算が有効であるか調べます。
   * 
   * @return 自動計算が有効であるかどうか
   */
  public boolean isRunning() {
    return this.computeTask != null;
  }

  /**
   * 統計の自動計算を停止します。
   */
  public void stop() {
    this.computeTask.cancel();
    this.updateTimer.purge();
    this.computeTask = null;
  }

  /**
   * 初期化します。
   */
  public synchronized void init() {
    this.submissionScoreMap.clear();
    this.maximumScore = computeMaximumScore();
  }

  /**
   * 提出物の点数取得率を取得します。
   * 
   * @param userId ユーザーID
   * @return 点数取得率
   */
  public synchronized double getPercentageOfSubmissionScore(String userId) {
    Double p = this.submissionScoreMap.get(userId);
    if (p == null) recompute();
    p = this.submissionScoreMap.get(userId);
    if (p == null) throw new IllegalArgumentException("Unknown user : " + userId); //$NON-NLS-1$

    // 課題がまだないのに点数があるということはどっちかというと1かな
    if (this.maximumScore == 0) return 1;

    return p.doubleValue() / this.maximumScore;
  }

  /**
   * 再計算を行います。
   */
  public synchronized void recompute() {
    init();
    for (final User student : User.getAllStudents()) {
      final String accountId = student.getAccount().getId();
      recompute(accountId);
    }
  }

  /**
   * 与えられたユーザーの再計算を行います。
   * 
   * @param accountId ユーザーのID
   */
  public synchronized void recompute(final String accountId) {
    final double score = computeUserScore(accountId);
    this.submissionScoreMap.put(accountId, Double.valueOf(score));
  }

  /**
   * 最大提出点数を計算します。
   * 
   * @return 最大提出点数
   */
  private static int computeMaximumScore() {
    int sum = 0;
    for (final Report report : Report.getAllReportsBeforeNow()) {
      sum += report.getPoint();
    }
    return sum;
  }

  /**
   * 単一生徒の成績を計算します。
   * 
   * @param userId ユーザーID
   * @return 成績
   */
  private static double computeUserScore(String userId) {
    double point = 0;
    for (Submission submission : Submission.getSubmissionsByAccountId(userId)) {
      double d = submission.getPoint();
      d /= 100;
      d *= submission.getReport().getPoint();
      point += d;
    }
    return point;
  }

}
