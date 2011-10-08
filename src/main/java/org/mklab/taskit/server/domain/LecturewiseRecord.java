/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;


/**
 * 講義別の成績を表すクラスです。
 * <p>
 * 講義情報、その講義でのある生徒の提出状況、出席状況を保持します。
 * 
 * @author ishikura
 */
public class LecturewiseRecord {

  private Lecture lecture;
  private Attendance attendance;
  private List<Submission> submissions;

  LecturewiseRecord(Lecture lecture, Attendance attendance, List<Submission> submissions) {
    super();
    this.lecture = lecture;
    this.attendance = attendance;
    this.submissions = submissions;
  }

  /**
   * 講義情報を取得します。
   * 
   * @return 講義情報
   */
  public Lecture getLecture() {
    return this.lecture;
  }

  /**
   * 出席状況を取得します。
   * 
   * @return 出席状況
   */
  public Attendance getAttendance() {
    return this.attendance;
  }

  /**
   * 提出状況を取得します。
   * 
   * @return 提出状況
   */
  public List<Submission> getSubmissions() {
    return this.submissions;
  }
}
