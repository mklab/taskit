/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;


/**
 * 単一生徒の成績を保持するクラスです。
 * 
 * @author ishikura
 */
public class StudentwiseRecord {

  private User student;
  private List<Submission> submissions;
  private List<Attendance> attendances;

  /**
   * {@link StudentwiseRecord}オブジェクトを構築します。
   */
  public StudentwiseRecord() {
    // for framework
  }

  StudentwiseRecord(User student, List<Submission> submissions, List<Attendance> attendances) {
    this.student = student;
    this.submissions = submissions;
    this.attendances = attendances;
  }

  /**
   * studentを取得します。
   * 
   * @return student
   */
  public User getStudent() {
    return this.student;
  }

  /**
   * 提出物のリストを取得します。
   * 
   * @return 提出物
   */
  public List<Submission> getSubmissions() {
    return this.submissions;
  }

  /**
   * 出席状況のリストを取得します。
   * 
   * @return 出席状況
   */
  public List<Attendance> getAttendances() {
    return this.attendances;
  }

}
