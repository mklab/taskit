/**
 * 
 */
package org.mklab.taskit.server;

import java.util.List;

import javax.persistence.EntityManager;

import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoImpl;
import org.mklab.taskit.server.dao.ReportDao;
import org.mklab.taskit.server.dao.ReportDaoImpl;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
class LectureQuery {

  /** for serialization. */
  private static final long serialVersionUID = 3530442985606745638L;
  private LectureDao lectureDao;
  private ReportDao reportDao;

  /**
   * {@link LectureServiceImpl}オブジェクトを構築します。
   */
  LectureQuery(EntityManager entityManager) {
    this.lectureDao = new LectureDaoImpl(entityManager);
    this.reportDao = new ReportDaoImpl(entityManager);
  }

  /**
   * 講義データを取得します。
   * 
   * @param index インデックス
   * @return 講義データ
   */
  LectureDto getLecture(int index) {
    final Lecture l = this.lectureDao.getAllLectures().get(index);
    final List<Report> reportList = this.reportDao.getReportsFromLectureId(l.getLectureId());
    return new LectureDto(l, reportList);
  }

  /**
   * 全講義データを取得します。
   * 
   * @return 全講義データ
   */
  LectureDto[] getAllLectures() {
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
   * 講義数を取得します。
   * 
   * @return 講義数
   */
  int getLectureCount() {
    return this.lectureDao.getLectureCount();
  }
}
