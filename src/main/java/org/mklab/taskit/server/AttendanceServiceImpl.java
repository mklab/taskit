/**
 * 
 */
package org.mklab.taskit.server;

import javax.persistence.EntityManager;

import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AttendanceDao;
import org.mklab.taskit.server.dao.AttendanceDaoImpl;
import org.mklab.taskit.shared.dto.AttendanceDto;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.service.AttendanceService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceServiceImpl extends TaskitRemoteService implements AttendanceService {

  /** for serialization. */
  private static final long serialVersionUID = 947515165315282345L;

  /**
   * @see org.mklab.taskit.shared.service.AttendanceService#setAttendanceType(java.lang.String,
   *      org.mklab.taskit.shared.model.Lecture, java.lang.String)
   */
  @Override
  public void setAttendanceType(String userName, Lecture lecture, String attendanceType) {
    final EntityManager entityManager = createEntityManager();
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(entityManager);
    attendanceDao.setAttendanceType(lecture.getLectureId(), userName, attendanceType);
  }

  /**
   * @see org.mklab.taskit.shared.service.AttendanceService#getAttendanceTypesOfStudents(org.mklab.taskit.shared.model.Lecture)
   */
  @Override
  public AttendanceDto[] getAttendanceTypesOfStudents(Lecture lecture) {
    // TODO Auto-generated method stub
    return null;
  }

}
