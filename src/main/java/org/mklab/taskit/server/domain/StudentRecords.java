/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
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

  /**
   * 与えられたIDの成績を取得します。
   * 
   * @param id ID
   * @return 成績
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static StudentRecords getRecordByAccountId(String id) {
    final User user = User.getUserByAccountId(id);
    final List<Lecture> lectures = Lecture.getAllLectures();
    return new StudentRecords(lectures, Arrays.asList(getRecord(user)));
  }

  private static StudentRecord getRecord(User user) {
    final List<Submission> submissions = Submission.getSubmissionsByAccountId(user.getAccount().getId());
    final List<Attendance> attendances = Attendance.getAttendancesByAccountId(user.getAccount().getId());

    final StudentRecord record = new StudentRecord(user, submissions, attendances);
    return record;
  }

  /**
   * 全生徒の成績を取得します。
   * 
   * @return 成績
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static StudentRecords getAllRecords() {
    final List<Lecture> lectures = Lecture.getAllLectures();
    final List<User> users = User.getAllStudents();
    final List<StudentRecord> recordList = new ArrayList<StudentRecord>();
    for (final User user : users) {
      recordList.add(getRecord(user));
    }
    return new StudentRecords(lectures, recordList);
  }

}
