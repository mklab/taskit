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
public class StudentRecords {

  private List<StudentRecord> records;

  private List<Lecture> lectures;

  StudentRecords(List<Lecture> lectures, List<StudentRecord> records) {
    super();
    this.lectures = lectures;
    this.records = records;
  }

  /**
   * recordsを取得します。
   * 
   * @return records
   */
  public List<StudentRecord> getRecords() {
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
