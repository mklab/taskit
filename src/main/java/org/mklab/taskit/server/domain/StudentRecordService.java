/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 学生の成績に関するサービスを提供するクラスです。
 * 
 * @author ishikura
 */
public class StudentRecordService {

  /**
   * 与えられたアカウントIDの生徒の、講義別成績を取得します。
   * 
   * @param accountId 生徒のID
   * @return 講義別成績
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static LecturewiseStudentRecords getLecturewiseRecordsByAccountId(String accountId) {
    final User user = User.getUserByAccountId(accountId);
    final List<Lecture> lectures = Lecture.getAllLectures();
    final Iterator<Attendance> attendances = Attendance.getAttendancesByAccountId(accountId).iterator();
    Attendance next = attendances.hasNext() ? attendances.next() : null;

    final List<LecturewiseStudentRecord> records = new ArrayList<LecturewiseStudentRecord>();
    for (final Lecture lecture : lectures) {
      List<Submission> submissions = Submission.getSubmissionByAccountIdAndLectureId(accountId, lecture.getId());
      Attendance attendance = null;
      if (next != null && next.getLecture().getId().equals(lecture.getId())) {
        attendance = next;
        next = attendances.hasNext() ? attendances.next() : null;
      }
      records.add(new LecturewiseStudentRecord(lecture, attendance, submissions));
    }

    return new LecturewiseStudentRecords(user, records);
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
