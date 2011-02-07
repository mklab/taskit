/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;


/**
 * 学生ひとりの全ての成績を表すテーブルです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class StudentwiseScoreTable implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 3161283166149426645L;
  private int scores[][];

  /**
   * {@link StudentwiseScoreTable}オブジェクトを構築します。
   */
  StudentwiseScoreTable() {
    // for serialization
  }

  /**
   * {@link StudentwiseScoreTable}オブジェクトを構築します。
   * 
   * @param scores 成績テーブル
   */
  public StudentwiseScoreTable(int[][] scores) {
    this.scores = scores;
  }

  /**
   * scoresを取得します。
   * 
   * @return scores
   */
  int[][] getScores() {
    return this.scores;
  }

  /**
   * scoresを設定します。
   * 
   * @param scores scores
   */
  void setScores(int[][] scores) {
    this.scores = scores;
  }

  /**
   * 成績を取得します。
   * 
   * @param lectureIndex 講義インデックス
   * @param reportIndex 課題インデックス
   * @return 成績
   */
  public int getScore(int lectureIndex, int reportIndex) {
    return this.scores[lectureIndex][reportIndex];
  }

}
