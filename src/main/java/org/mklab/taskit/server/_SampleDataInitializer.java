/**
 * 
 */
package org.mklab.taskit.server;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AttendanceDao;
import org.mklab.taskit.server.dao.AttendanceTypeDao;
import org.mklab.taskit.server.dao.AttendanceTypeDaoImpl;
import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoImpl;
import org.mklab.taskit.server.dao.ReportDao;
import org.mklab.taskit.server.dao.ReportDaoImpl;
import org.mklab.taskit.server.dao.ReportRegistrationException;
import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.shared.model.AttendanceType;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.model.UserType;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */

@SuppressWarnings("all")
public class _SampleDataInitializer {

  public static void main(String[] args) {
    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$
    final EntityManager entityManager = entityManagerFactory.createEntityManager();

    createAccounts(new AccountDaoImpl(entityManager));
    createLectures(new LectureDaoImpl(entityManager));
    createReports(new ReportDaoImpl(entityManager));
    entityManagerFactory.close();
  }

  private static void createAccounts(final AccountDao dao) {
    Account.registerNewAccount("koga", Passwords.hashPassword("taskit"), UserType.TEACHER);
    for (int i = 0; i < 5; i++) {
      Account.registerNewAccount(String.valueOf(10675001 + i), "taskit", UserType.TA);
    }
    for (int i = 0; i < 10; i++) {
      Account.registerNewAccount(String.valueOf(10236001 + i), "taskit", UserType.STUDENT);
    }
  }

  private static void createLectures(final LectureDao dao) {
    dao.registerLecture(new Lecture("Hello world1", System.currentTimeMillis()));
    dao.registerLecture(new Lecture("Hello world2", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));
    dao.registerLecture(new Lecture("Hello world3", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14)));
  }

  private static void createReports(final ReportDao dao) {
    try {
      dao.registerReport(new Report(1, "report1-1", "asdfasf", 1, 1));
      dao.registerReport(new Report(2, "report1-2", "asdfasf", 2, 1));
      dao.registerReport(new Report(1, "report2-1", "asdfasf", 1, 2));
      dao.registerReport(new Report(2, "report2-1", "asdfasf", 2, 2));
    } catch (ReportRegistrationException e) {
      throw new RuntimeException(e);
    }
  }

}
