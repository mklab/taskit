/**
 * 
 */
package org.mklab.taskit.server;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AttendanceDao;
import org.mklab.taskit.server.dao.AttendanceDaoImpl;
import org.mklab.taskit.server.dao.AttendanceTypeDao;
import org.mklab.taskit.server.dao.AttendanceTypeDaoImpl;
import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoImpl;
import org.mklab.taskit.shared.dto.AttendanceBaseDto;
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
   *      int, java.lang.String)
   */
  @Override
  public void setAttendanceType(String userName, int lectureIndex, String attendanceType) {
    SessionUtil.assertIsTAOrTeacher(getSession());

    final EntityManager entityManager = createEntityManager();
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(entityManager);
    final Lecture lecture = getLectureOf(lectureIndex, entityManager);
    attendanceDao.setAttendanceType(lecture.getLectureId(), userName, attendanceType);
  }

  private Lecture getLectureOf(int lectureIndex, final EntityManager entityManager) {
    final LectureDao lectureDao = new LectureDaoImpl(entityManager);

    final Lecture lecture = lectureDao.getAllLectures().get(lectureIndex);
    return lecture;
  }

  /**
   * @see org.mklab.taskit.shared.service.AttendanceService#getLecturewiseAttendanceData(int)
   */
  @Override
  public AttendanceDto getLecturewiseAttendanceData(int lectureIndex) {
    SessionUtil.assertIsTAOrTeacher(getSession());

    final EntityManager entityManager = createEntityManager();
    final AttendanceDao attendanceDao = new AttendanceDaoImpl(entityManager);

    final Lecture lecture = getLectureOf(lectureIndex, entityManager);
    final Map<String, Integer> attendances = attendanceDao.getAllStudentAttendanceDataFromLectureId(lecture.getLectureId());
    for (Entry<String, Integer> entry : attendances.entrySet()) {
      entry.setValue(Integer.valueOf(entry.getValue().intValue() - 1));
    }
    return new AttendanceDto(lecture, attendances);
  }

  /**
   * @see org.mklab.taskit.shared.service.AttendanceService#getBaseData()
   */
  @Override
  public AttendanceBaseDto getBaseData() {
    SessionUtil.assertIsTAOrTeacher(getSession());

    final EntityManager entityManager = createEntityManager();
    final AccountDao accountDao = new AccountDaoImpl(entityManager);
    final LectureDao lectureDao = new LectureDaoImpl(entityManager);
    final AttendanceTypeDao attendanceTypeDao = new AttendanceTypeDaoImpl(entityManager);

    final List<String> userNames = accountDao.getAllStudentUserNames();
    final int lectureCount = lectureDao.getLectureCount();
    final List<String> attendanceTypes = attendanceTypeDao.getAllAttendanceTypes();

    return new AttendanceBaseDto(lectureCount, userNames, attendanceTypes);
  }
}
