/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Attendance;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public class AttendanceDaoImpl extends AbstractDao implements AttendanceDao {

  /**
   * {@link AttendanceDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AttendanceDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceStateFromAccountId(int)
   */
  @Override
  public List<Attendance> getAttendanceStateFromAccountId(int accountId) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.accountId = :accountId"); //$NON-NLS-1$
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
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT a FROM ATTENDANCE a WHERE a.lectureId = :lectureId"); //$NON-NLS-1$
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
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT t.type FROM ATTENDANCE a, ATTENDANCE_TYPE t WHERE a.lectureId = :lectureId_ AND a.attendanceTypeId=t.attendanceTypeId ORDER BY a.accountId ASC"); //$NON-NLS-1$
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
    final EntityManager entityManager = entityManager();
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();
    Query q1 = entityManager.createQuery("SELECT type.attendanceTypeId FROM ATTENDANCE_TYPE type WHERE type.type = :attendanceType"); //$NON-NLS-1$
    q1.setParameter("attendanceType", attendanceType); //$NON-NLS-1$
    int attendanceTypeId = (Integer)q1.getSingleResult();
    Query q2 = entityManager.createQuery("SELECT account.accountId FROM ACCOUNT account WHERE account.userName = :userName"); //$NON-NLS-1$
    q2.setParameter("userName", userName); //$NON-NLS-1$
    int accountId = (Integer)q2.getSingleResult();
    Query query = entityManager.createQuery("UPDATE ATTENDANCE a SET a.attendanceTypeId = :attendanceTypeId WHERE a.accountId = :accountId AND a.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("attendanceTypeId", attendanceTypeId); //$NON-NLS-1$
    query.setParameter("accountId", accountId); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    int executedCount = 0;
    try {
      executedCount = query.executeUpdate();
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
    if (executedCount == 0) {
      registerAttendance(new Attendance(attendanceTypeId, false, false, lectureId, accountId));
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#registerAttendance(org.mklab.taskit.shared.model.Attendance)
   */
  @Override
  public void registerAttendance(Attendance attendance) {
    final EntityManager entityManager = entityManager();
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();
    try {
      entityManager.persist(attendance);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAllStudentAttendanceDataFromLectureId(int)
   */
  @SuppressWarnings("boxing")
  @Override
  public Map<String, Integer> getAllStudentAttendanceDataFromLectureId(int lectureId) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager
        .createQuery("SELECT account.userName,attendance.attendanceTypeId FROM ATTENDANCE attendance, ACCOUNT account WHERE attendance.lectureId = :lectureId AND attendance.accountId = account.accountId"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$

    Map<String, Integer> userNameToAttendanceType = new HashMap<String, Integer>();
    @SuppressWarnings("unchecked")
    List<Object[]> list = query.getResultList();
    for (Object[] userNameAttendance : list) {
      userNameToAttendanceType.put((String)userNameAttendance[0], (Integer)userNameAttendance[1]);
    }
    return userNameToAttendanceType;
  }
}
