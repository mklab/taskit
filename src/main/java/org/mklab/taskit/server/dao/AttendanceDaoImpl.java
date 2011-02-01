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

  /** エンティティマネージャ */
  private EntityManager entityManager;

  /**
   * {@link AttendanceDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AttendanceDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceStateFromAccountId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromAccountId(int accountId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.accountId = :accountId"); //$NON-NLS-1$
    query.setParameter("accountId", Integer.valueOf(accountId)); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    List<Attendance> attendances = query.getResultList();

    return attendances;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceStateFromLessonId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromLessonId(int lectureId) {
    Query query = this.entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("lectureId", Integer.valueOf(lectureId)); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    List<Attendance> attendances = query.getResultList();
    return attendances;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceTypes(int)
   */
  @SuppressWarnings("boxing")
  @Override
  public List<String> getAttendanceTypes(int lectureId) {
    Query query = this.entityManager
        .createQuery("SELECT t.type FROM ATTENDANCE a, ATTENDANCE_TYPE t WHERE a.lectureId = :lectureId_ AND a.attendanceTypeId=t.attendanceTypeId ORDER BY a.accountId ASC"); //$NON-NLS-1$
    query.setParameter("lectureId_", lectureId); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    List<String> attendanceTypes = query.getResultList();
    return attendanceTypes;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#setAttendanceType(int,
   *      String, String)
   */
  @SuppressWarnings("boxing")
  @Override
  public void setAttendanceType(int lectureId, String userName, String attendanceType) {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    StringBuilder sb = new StringBuilder();
    sb.append("UPDATE ATTENDANCE a "); //$NON-NLS-1$
    sb.append("SET a.attendanceTypeId = type.attendanceTypeId "); //$NON-NLS-1$
    sb.append("FROM ACCOUNT account, ATTENDANCE_TYPE type "); //$NON-NLS-1$
    sb.append("WHERE a.lectureId = :lectureId AND a.accountId = account.accountId AND account.userName = :userName AND type.attendanceType = :attendanceType"); //$NON-NLS-1$
    String ejbqlString = sb.toString();
    Query query = this.entityManager.createQuery(ejbqlString);
    query.setParameter("attendanceType", attendanceType); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    query.executeUpdate();
    try {
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#registerAttendance(org.mklab.taskit.shared.model.Attendance)
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
