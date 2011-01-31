/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mklab.taskit.shared.model.Report;


/**
 * @author teshima
 * @version $Revision$, Jan 26, 2011
 */
public class DataAccessMain {

  public static void main(String[] args) throws ReportRegistrationException {
    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("taskit");
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(entityManager);
    attendanceDao.getAttendanceState("10236001");
  }
}
