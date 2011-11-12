/**
 * 
 */
package org.mklab.taskit.server.domain;

/**
 * 個人の順位や得点、統計情報を含む成績を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class Record {

  /** ユーザーIDです。 */
  String accountId;
  /** 全生徒の統計情報です。 */
  Statistics statistics;
  /** 全生徒中のランキングです。 */
  int rank;
  /** 得点です。 */
  double score;
  /** 偏差値です。 */
  double deviation;

  /**
   * {@link Record}オブジェクトを構築します。
   */
  public Record() {
    // only for serialization.
  }

  Record(String accountId) {
    if (accountId == null) throw new NullPointerException();
    this.accountId = accountId;
  }

  /**
   * ユーザーアカウントのIDを取得します。
   * 
   * @return ユーザーアカウントのID
   */
  public String getAccountId() {
    return this.accountId;
  }

  /**
   * 全生徒の統計情報を取得します。
   * 
   * @return 全生徒の統計情報
   */
  public Statistics getStatistics() {
    return this.statistics;
  }

  /**
   * 全生徒の統計情報を設定します。
   * 
   * @param statistics 全生徒の統計情報
   */
  public void setStatistics(Statistics statistics) {
    this.statistics = statistics;
  }

  /**
   * 順位を取得します。
   * 
   * @return 順位
   */
  public int getRank() {
    return this.rank;
  }

  /**
   * 順位を設定します。
   * 
   * @param rank 順位
   */
  public void setRank(int rank) {
    this.rank = rank;
  }

  /**
   * 得点を設定します。
   * 
   * @param score 得点
   */
  public void setScore(double score) {
    this.score = score;
  }

  /**
   * 得点を取得します。
   * 
   * @return 得点
   */
  public double getScore() {
    return this.score;
  }

  /**
   * 偏差値を取得します。
   * 
   * @return 偏差値
   */
  public double getDeviation() {
    return this.deviation;
  }

  /**
   * 偏差値を設定します。
   * 
   * @param deviation 偏差値
   */
  public void setDeviation(double deviation) {
    this.deviation = deviation;
  }

}
