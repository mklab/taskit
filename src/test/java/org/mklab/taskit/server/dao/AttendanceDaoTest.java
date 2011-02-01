/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mklab.taskit.shared.model.Attendance;
import org.mklab.taskit.shared.model.AttendanceType;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDaoTest extends DaoTest {

  @Test
  public void testSetAttendanceType() {
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(createEntityManager());
    Attendance attendance = new Attendance(0, false, false, 0, 0);
    attendanceDao.registerAttendance(attendance);
    List<String> actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    System.out.println(actualAttendanceTypes.get(0));
    List<String> expectedAttendanceTypes = new ArrayList<String>();
    expectedAttendanceTypes.add("test");
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);

    attendanceDao.setAttendanceType(0, 0, 0);
    actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);
  }

  @Test
  public void testGetAttendanceTypes() {
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(createEntityManager());
    Attendance attendance = new Attendance(0, false, false, 0, 0);
    attendanceDao.registerAttendance(attendance);
    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(createEntityManager());
    AttendanceType type = new AttendanceType("test");
    typeDao.registerAttendanceType(type);
    List<String> attendanceTypes = attendanceDao.getAttendanceTypes(0);
    assertEquals("test", attendanceTypes.get(0));
  }
}
