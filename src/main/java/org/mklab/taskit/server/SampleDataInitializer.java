/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.User;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.UserType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */

@SuppressWarnings("all")
public class SampleDataInitializer {

  public static void main(String[] args) {
    createAccounts();
    createLectures();
    createAttendances();
    createSubmissions();
  }

  private static void createAccounts() {
    Account.registerNewAccount("koga", "taskit", UserType.TEACHER);
    for (int i = 0; i < 5; i++) {
      Account.registerNewAccount(String.valueOf(10675001 + i), "taskit", UserType.TA);
    }
    for (int i = 0; i < 10; i++) {
      Account.registerNewAccount(String.valueOf(10236001 + i), "taskit", UserType.STUDENT);
    }
  }

  private static void createLectures() {
    for (int i = 0; i < 15; i++) {
      Lecture lecture = createLecture(i + 1, (int)(Math.random() * 5));
      lecture.persist();
    }
  }

  private static Lecture createLecture(int lectureNumber, int reportCount) {
    Lecture lecture = new Lecture();
    lecture.setTitle("lecture" + lectureNumber);
    lecture.setDescription("This is lecture" + lectureNumber + ".");
    lecture.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7 * lectureNumber - 365)));

    List<Report> reports = new ArrayList<Report>();
    for (int i = 0; i < reportCount; i++) {
      Report report = new Report(MessageFormat.format("report{0}-{1}", lectureNumber, reportCount), 10 * (i + 1), lecture);
      report.setDescription("This is report" + (i + 1) + ".");
      reports.add(report);
    }
    lecture.setReports(reports);
    return lecture;
  }

  private static void createAttendances() {
    final List<User> students = User.getAllStudents();
    final List<Lecture> lectures = Lecture.getAllLectures();

    for (User student : students) {
      for (Lecture lecture : lectures) {
        Attendance a = new Attendance(AttendanceType.values()[(int)(Math.random() * AttendanceType.values().length)], student.getAccount(), lecture);
        a.persist();
      }
    }
  }

  private static void createSubmissions() {
    final List<User> students = User.getAllStudents();
    final List<Lecture> lectures = Lecture.getAllLectures();

    for (User student : students) {
      for (Lecture lecture : lectures) {
        for (Report report : lecture.getReports()) {
          int point = ((int)(Math.random() * 4)) * 50;
          if (point > 100) {
            continue;
          }

          Submission submission = new Submission(point, student.getAccount(), report);
          submission.persist();
        }
      }
    }
  }

}
