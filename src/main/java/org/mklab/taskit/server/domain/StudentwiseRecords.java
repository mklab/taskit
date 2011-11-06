/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;


/**
 * 全ての生徒の成績を保持するクラスです。
 * 
 * @author ishikura
 */
public class StudentwiseRecords {

  private List<StudentwiseRecord> records;

  private List<Lecture> lectures;

  /**
   * {@link StudentwiseRecords}オブジェクトを構築します。
   */
  public StudentwiseRecords() {
    // for framework
  }

  StudentwiseRecords(List<Lecture> lectures, List<StudentwiseRecord> records) {
    super();
    this.lectures = lectures;
    this.records = records;
  }

  /**
   * recordsを取得します。
   * 
   * @return records
   */
  public List<StudentwiseRecord> getRecords() {
    return this.records;
  }

  /**
   * lecturesを取得します。
   * 
   * @return lectures
   */
  public List<Lecture> getLectures() {
    return this.lectures;
  }

}
