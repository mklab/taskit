/**
 * 
 */
package org.mklab.taskit.server;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AttendanceDao;
import org.mklab.taskit.server.dao.AttendanceTypeDao;
import org.mklab.taskit.server.dao.AttendanceTypeDaoImpl;
import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.shared.model.AttendanceType;
import org.mklab.taskit.shared.model.UserType;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */

@SuppressWarnings("all")
public class _SampleDataInitializer {

  public static void main(String[] args) {
    createAccounts();
    createLectures();
    //    createLectures(new LectureDaoImpl(entityManager));
    //    createReports(new ReportDaoImpl(entityManager));
  }

  private static void createAccounts() {
    Account.registerNewAccount("koga", Passwords.hashPassword("taskit"), UserType.TEACHER);
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
    lecture.setDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7 * lectureNumber)));

    List<Report> reports = new ArrayList<Report>();
    for (int i = 0; i < reportCount; i++) {
      Report report = new Report(MessageFormat.format("report{0}-{1}", lectureNumber, reportCount), 10 * (i + 1), lecture);
      report.setDescription("This is report" + (i + 1) + ".");
      reports.add(report);
    }
    lecture.setReports(reports);
    return lecture;
  }

}
