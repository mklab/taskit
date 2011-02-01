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
    Attendance attendance = new Attendance(1, false, false, 0, 0);
    attendanceDao.registerAttendance(attendance);
    final AttendanceTypeDao typeDao = new AttendanceTypeDaoImpl(createEntityManager());
    typeDao.registerAttendanceType(new AttendanceType("absent"));
    typeDao.registerAttendanceType(new AttendanceType("attend"));
    List<String> actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    
    List<String> expectedAttendanceTypes = new ArrayList<String>();
    expectedAttendanceTypes.add("absent");
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);

    attendanceDao.setAttendanceType(0, 0, 2);
    actualAttendanceTypes = attendanceDao.getAttendanceTypes(0);
    assertEquals(expectedAttendanceTypes, actualAttendanceTypes);
  }

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
    AttendanceType type_absent = new AttendanceType("absent");
    AttendanceType type_attend = new AttendanceType("attend");
    typeDao.registerAttendanceType(type_absent);
    typeDao.registerAttendanceType(type_attend);
    
    List<String> attendanceTypes = attendanceDao.getAttendanceTypes(0);
    assertEquals("absent", attendanceTypes.get(0));
    assertEquals("absent", attendanceTypes.get(1));
    assertEquals("absent", attendanceTypes.get(2));
    assertEquals("attend", attendanceTypes.get(3));
    assertEquals("attend", attendanceTypes.get(4));
    assertEquals("attend", attendanceTypes.get(5));
  }
}
