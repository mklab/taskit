/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Attendance;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public class AttendanceDaoImpl implements AttendanceDao {

  private EntityManager entityManager;

  public AttendanceDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceStateFromAccountId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromAccountId(int accountId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.accountId = :accountId"); //$NON-NLS-1$
    query.setParameter("accountId", accountId); //$NON-NLS-1$
    List<Attendance> attendances = (List<Attendance>)query.getResultList();

    return attendances;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceStateFromLessonId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromLessonId(int lessonId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.lessonId = :lessonId"); //$NON-NLS-1$
    query.setParameter("lessonId", lessonId); //$NON-NLS-1$
    List<Attendance> attendances = query.getResultList();
    return attendances;
  }

}
