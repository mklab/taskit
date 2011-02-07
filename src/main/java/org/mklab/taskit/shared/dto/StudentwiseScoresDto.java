/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class StudentwiseScoresDto implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -7917810950663273040L;
  private LectureDto[] lectures;
  private StudentwiseScoreTable scoreTable;

  /**
   * {@link StudentwiseScoresDto}オブジェクトを構築します。
   * 
   * @param lectures 講義データ
   * @param scoreTable 成績データ
   */
  public StudentwiseScoresDto(LectureDto[] lectures, StudentwiseScoreTable scoreTable) {
    super();
    this.lectures = lectures;
    this.scoreTable = scoreTable;
  }

  /**
   * {@link StudentwiseScoresDto}オブジェクトを構築します。
   */
  StudentwiseScoresDto() {
    // for serialization
  }

  /**
   * 講義データを取得します。
   * 
   * @param index インデックス
   * @return 講義データ
   */
  public LectureDto getLecture(int index) {
    return this.lectures[index];
  }

  /**
   * 講義数を取得します。
   * 
   * @return 講義数
   */
  public int getLectureCount() {
    return this.lectures.length;
  }

  /**
   * lecturesを取得します。
   * 
   * @return lectures
   */
  LectureDto[] getLectures() {
    return this.lectures;
  }

  /**
   * lecturesを設定します。
   * 
   * @param lectures lectures
   */
  void setLectures(LectureDto[] lectures) {
    this.lectures = lectures;
  }

  /**
   * scoreTableを取得します。
   * 
   * @return scoreTable
   */
  public StudentwiseScoreTable getScoreTable() {
    return this.scoreTable;
  }

  /**
   * scoreTableを設定します。
   * 
   * @param scoreTable scoreTable
   */
  void setScoreTable(StudentwiseScoreTable scoreTable) {
    this.scoreTable = scoreTable;
  }

}
