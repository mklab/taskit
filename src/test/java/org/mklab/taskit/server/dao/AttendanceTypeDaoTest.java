/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.junit.Test;
import org.mklab.taskit.shared.model.AttendanceType;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceTypeDaoTest extends DaoTest {

  /**
   * {@link AttendanceTypeDao#registerAttendanceType(AttendanceType)}のテストを行ないます。
   */
  @Test
  public void testRegisterAttendanceType() {
    final AttendanceTypeDao dao = new AttendanceTypeDaoImpl(createEntityManager());
    AttendanceType attendance_absent = new AttendanceType("absent"); //$NON-NLS-1$
    dao.registerAttendanceType(attendance_absent);
    AttendanceType attendance_attend = new AttendanceType("attend"); //$NON-NLS-1$
    dao.registerAttendanceType(attendance_attend);

    List<String> allAttendanceTypes = dao.getAllAttendanceTypes();
    System.out.println(allAttendanceTypes);
  }
}
