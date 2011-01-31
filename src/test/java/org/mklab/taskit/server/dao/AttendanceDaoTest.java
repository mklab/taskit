/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mklab.taskit.shared.model.Attendance;
import org.mklab.taskit.shared.model.AttendanceType;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDaoTest extends DaoTest {

  /**
   * 出席状況変更のテストを行ないます。
   * 
   * @throws AccountRegistrationException
   */
  @Test
  public void testSetAttendanceType() throws AccountRegistrationException {
    final AccountDao accountDao = new AccountDaoImpl(createEntityManager());
    accountDao.registerAccount("10236001", "taskit", "STUDENT"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    accountDao.registerAccount("10236002", "taskit", "STUDENT"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
    accountDao.registerAccount("10236003", "taskit", "STUDENT"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    final AttendanceDao attendanceDao = new AttendanceDaoImpl(createEntityManager());
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 1));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 2));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 3));

    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(createEntityManager());
    typeDao.registerAttendanceType(new AttendanceType("absent")); //$NON-NLS-1$
    typeDao.registerAttendanceType(new AttendanceType("attend")); //$NON-NLS-1$
    List<String> actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);

    List<String> expectedAttendanceTypes = new ArrayList<String>();
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);

    //    final Account dummyUser = createUniqueUser("TA"); //$NON-NLS-1$
    //    attendanceDao.setAttendanceType(0, dummyUser.getUserName(), "attend"); //$NON-NLS-1$
    attendanceDao.setAttendanceType(0, "10236001", "attend"); //$NON-NLS-1$ //$NON-NLS-2$
    actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    expectedAttendanceTypes.clear();
    expectedAttendanceTypes.add("attend"); //$NON-NLS-1$
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
    expectedAttendanceTypes.add("absent"); //$NON-NLS-1$
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

  /**
   * 講義IDの全生徒の出席データを取得できるかどうかテストします。
   */
  @Test
  public void testGetAllStudentAttendanceDataFromLectureId() {
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(createEntityManager());
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 0));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 1));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 2));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 0, 3));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 0, 4));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 0, 5));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 1, 0));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 1, 1));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 1, 2));
    attendanceDao.registerAttendance(new Attendance(2, false, false, 1, 3));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 1, 4));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 1, 5));
    attendanceDao.registerAttendance(new Attendance(1, false, false, 1, 6));

    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(createEntityManager());
    AttendanceType type_absent = new AttendanceType("absent"); //$NON-NLS-1$
    AttendanceType type_attend = new AttendanceType("attend"); //$NON-NLS-1$
    typeDao.registerAttendanceType(type_absent);
    typeDao.registerAttendanceType(type_attend);

    // ユーザー名が必要となったため、上の準備だけでは足りず、アカウントも登録して置かなければテストできない
/*    Map<String, Integer> attendances_lecture0 = attendanceDao.getAllStudentAttendanceDataFromLectureId(0);
    assertEquals(6, attendances_lecture0.size());
    Map<String, Integer> attendances_lecture1 = attendanceDao.getAllStudentAttendanceDataFromLectureId(1);
    assertEquals(7, attendances_lecture1.size());
    assertEquals(2, attendances_lecture0.get(5).intValue());*/

  }
}
