/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
   * @see org.mklab.taskit.server.dao.AttendanceDaoTest#getAttendanceStateFromAccountId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromAccountId(int accountId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.accountId = :accountId"); //$NON-NLS-1$
    query.setParameter("accountId", accountId); //$NON-NLS-1$
    List<Attendance> attendances = (List<Attendance>)query.getResultList();

    return attendances;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDaoTest#getAttendanceStateFromLessonId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromLessonId(int lectureId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    List<Attendance> attendances = query.getResultList();
    return attendances;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDaoTest#getAttendanceTypes(int)
   */
  @SuppressWarnings("boxing")
  @Override
  public List<String> getAttendanceTypes(int lectureId) {
    Query query = this.entityManager.createQuery("SELECT t.type FROM ATTENDANCE a, ATTENDANCE_TYPE t WHERE a.lectureId = :lectureId_ AND a.attendanceTypeId=t.attendanceTypeId ORDER BY a.accountId ASC"); //$NON-NLS-1$
    query.setParameter("lectureId_", lectureId); //$NON-NLS-1$
    List<String> attendanceTypes = query.getResultList();
    return attendanceTypes;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#setAttendanceType(int, int,
   *      int)
   */
  @SuppressWarnings("boxing")
  @Override
  public void setAttendanceType(int lectureId, int accountId, int attendanceType) {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    Query query = this.entityManager.createQuery("UPDATE ATTENDANCE a SET a.attendanceType = :attendanceType_ WHERE a.lectureId = :lectureId_ AND a.accountId = :accountId_"); //$NON-NLS-1$
    query.setParameter("attendanceType_", attendanceType); //$NON-NLS-1$
    query.setParameter("lectureId_", lectureId); //$NON-NLS-1$
    query.setParameter("accountId_", accountId); //$NON-NLS-1$
    query.executeUpdate();
    try {
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }

  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#registerAttendance(int, int,
   *      String)
   */
  @Override
  public void registerAttendance(Attendance attendance) {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(attendance);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
  }
}
