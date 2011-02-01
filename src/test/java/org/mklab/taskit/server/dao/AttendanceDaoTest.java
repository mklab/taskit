/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Test;
import org.mklab.taskit.shared.model.Account;
import org.mklab.taskit.shared.model.Attendance;
import org.mklab.taskit.shared.model.AttendanceType;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDaoTest extends DaoTest {

  /**
   * 出席状況変更のテストを行ないます。
   */
  @Test
  public void testSetAttendanceType() {
    final EntityManager entityManager = Persistence.createEntityManagerFactory("taskit-test").createEntityManager(); //$NON-NLS-1$
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(entityManager);
    final Attendance attendance = new Attendance(1, false, false, 0, 0);
    attendanceDao.registerAttendance(attendance);
    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(entityManager);
    typeDao.registerAttendanceType(new AttendanceType("absent")); //$NON-NLS-1$
    typeDao.registerAttendanceType(new AttendanceType("attend")); //$NON-NLS-1$
    List<String> actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);

    List<String> expectedAttendanceTypes = new ArrayList<String>();
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);

    final Account dummyUser = createUniqueUser("TA"); //$NON-NLS-1$
    attendanceDao.setAttendanceType(0, dummyUser.getUserName(), "attend"); //$NON-NLS-1$
    actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    expectedAttendanceTypes.remove(0);
    expectedAttendanceTypes.add("attend"); //$NON-NLS-1$
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);
  }

  /**
   * 全学生の出席状況を取得するメソッドのテストを行ないます。
   */
  @Test
  public void testGetAttendanceTypes() {
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(createEntityManager());
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 0));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 1));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 2));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 0, 3));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 0, 4));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 0, 5));

    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(createEntityManager());
    AttendanceType type_absent = new AttendanceType("absent"); //$NON-NLS-1$
    AttendanceType type_attend = new AttendanceType("attend"); //$NON-NLS-1$
    typeDao.registerAttendanceType(type_absent);
    typeDao.registerAttendanceType(type_attend);

    List<String> attendanceTypes = attendanceDao.getAttendanceTypes(0);
    System.out.println(attendanceTypes);
    assertEquals("absent", attendanceTypes.get(0)); //$NON-NLS-1$
    assertEquals("absent", attendanceTypes.get(1)); //$NON-NLS-1$
    assertEquals("absent", attendanceTypes.get(2)); //$NON-NLS-1$
    assertEquals("attend", attendanceTypes.get(3)); //$NON-NLS-1$
    assertEquals("attend", attendanceTypes.get(4)); //$NON-NLS-1$
    assertEquals("attend", attendanceTypes.get(5)); //$NON-NLS-1$
  }

}
