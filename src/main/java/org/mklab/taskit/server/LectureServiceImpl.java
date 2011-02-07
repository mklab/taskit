/**
 * 
 */
package org.mklab.taskit.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoImpl;
import org.mklab.taskit.server.dao.ReportDao;
import org.mklab.taskit.server.dao.ReportDaoImpl;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.service.LectureService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class LectureServiceImpl extends TaskitRemoteService implements LectureService {

  /** for serialization. */
  private static final long serialVersionUID = 3530442985606745638L;
  private LectureDao lectureDao;
  private ReportDao reportDao;

  /**
   * {@link LectureServiceImpl}オブジェクトを構築します。
   */
  public LectureServiceImpl() {
    final EntityManager entityManager = createEntityManager();
    this.lectureDao = new LectureDaoImpl(entityManager);
    this.reportDao = new ReportDaoImpl(entityManager);
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getLecture(int)
   */
  @Override
  public LectureDto getLecture(int index) {
    final Lecture l = this.lectureDao.getAllLectures().get(index);
    final List<Report> reportList = this.reportDao.getReportsFromLectureId(l.getLectureId());
    return new LectureDto(l, reportList);
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getAllLectures()
   */
  @Override
  public LectureDto[] getAllLectures() {
    final List<Lecture> lectures = this.lectureDao.getAllLectures();
    final LectureDto[] lectureDtoList = new LectureDto[lectures.size()];
    for (int i = 0; i < lectures.size(); i++) {
      final Lecture lecture = lectures.get(i);
      final List<Report> reportList = this.reportDao.getReportsFromLectureId(lecture.getLectureId());

      lectureDtoList[i] = new LectureDto(lecture, reportList);
    }
    return lectureDtoList;
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getLectureCount()
   */
  @Override
  public int getLectureCount() {
    return this.lectureDao.getLectureCount();
  }

}
