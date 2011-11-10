/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


/**
 * 学生の成績の統計を行うクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class StatisticsCollector {

  /*
   * TODO 適切なロック
   * 今はとりあえず全部synchronizedにすることで並列対応している
   */

  Map<String, Record> recordMap;
  Statistics statistics;
  Timer updateTimer;
  TimerTask computeTask;

  /**
   * {@link StatisticsCollector}オブジェクトを構築します。
   */
  public StatisticsCollector() {
    this.recordMap = new HashMap<String, Record>();
    this.updateTimer = new Timer("TASKit Statistics Updater"); //$NON-NLS-1$
    this.statistics = new Statistics();
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
    this.recordMap.clear();
    this.statistics.setMaximumScore(computeMaximumScore());
  }

  /**
   * 与えられたユーザーの成績情報を取得します。
   * 
   * @param userId ユーザーID
   * @return 成績情報
   */
  public synchronized Record getRecord(String userId) {
    Record record = this.recordMap.get(userId);
    if (record == null) {
      recompute();
    }
    if (record == null) {
      throw new IllegalArgumentException("Unknown user : " + userId); //$NON-NLS-1$
    }
    return record;
  }

  /**
   * 再計算を行います。
   */
  public synchronized void recompute() {
    init();
    final List<User> allStudents = User.getAllStudents();
    this.statistics.setStudentCount(allStudents.size());
    for (final User student : allStudents) {
      final String studentId = student.getAccount().getId();
      final Record record = getOrCreateRecord(studentId);
      record.setScore(computeUserScore(studentId));
      recompute(student.getAccount().getId());
    }

    updateStatistics();
  }

  private Record getOrCreateRecord(final String accountId) {
    Record record = this.recordMap.get(accountId);
    if (record == null) {
      record = new Record();
      this.recordMap.put(accountId, record);
    }
    return record;
  }

  /**
   * 与えられたユーザーの再計算を行います。
   * 
   * @param accountId ユーザーのID
   */
  public synchronized void recompute(final String accountId) {
    final Record record = getOrCreateRecord(accountId);
    final double score = computeUserScore(accountId);
    record.setScore(score);

    updateStatistics();
  }

  /**
   * 全体の平均、ランキング、偏差値を更新します。
   */
  private synchronized void updateStatistics() {
    final Collection<Record> allRecords = this.recordMap.values();
    final int studentCount = allRecords.size();

    double average = 0;
    for (final Record record : allRecords) {
      average += record.getScore();
    }
    average /= studentCount;

    double standardDeviation = 0;
    for (final Record record : allRecords) {
      standardDeviation += Math.pow(record.getScore() - average, 2);
    }
    standardDeviation = Math.pow(standardDeviation / studentCount, 0.5);

    this.statistics.setAverage(average);
    this.statistics.setStandardDeviation(standardDeviation);
    this.statistics.setStudentCount(studentCount);

    for (final Record record : allRecords) {
      final double deviation = 10 * (record.getScore() - average) / standardDeviation + 50;
      record.setDeviation(deviation);
      record.setStatistics(this.statistics);
    }

    final TreeSet<Record> sortedRecord = new TreeSet<Record>(new Comparator<Record>() {

      @Override
      public int compare(Record o1, Record o2) {
        return o1.getScore() > o2.getScore() ? -1 : o1.getScore() < o2.getScore() ? 1 : 0;
      }
    });
    sortedRecord.addAll(allRecords);

    int rank = 1;
    double lastScore = Double.NEGATIVE_INFINITY;
    int lastRank = Integer.MIN_VALUE;
    for (Record record : sortedRecord) {
      if (record.getScore() == lastScore) {
        record.setRank(lastRank);
      } else {
        record.setRank(rank);
        lastRank = rank;
      }
      lastScore = record.getScore();
      rank++;
    }
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
