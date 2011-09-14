/**
 * 
 */
package org.mklab.taskit.server;

import java.util.List;

import org.mklab.taskit.server.dao.DaoFactory;
import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoFactory;
import org.mklab.taskit.server.dao.ReportDao;
import org.mklab.taskit.server.dao.ReportDaoFactory;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
class LectureQuery {

  private DaoFactory<ReportDao> reportDaoFactory = new ReportDaoFactory();
  private DaoFactory<LectureDao> lectureDaoFactory = new LectureDaoFactory();

  /**
   * 講義データを取得します。
   * 
   * @param index インデックス
   * @return 講義データ
   */
  LectureDto getLecture(int index) {
    final LectureDao lectureDao = this.lectureDaoFactory.create();
    final Lecture l = lectureDao.getAllLectures().get(index);
    lectureDao.close();

    final ReportDao reportDao = this.reportDaoFactory.create();
    final List<Report> reportList = reportDao.getReportsFromLectureId(l.getLectureId());
    reportDao.close();

    return new LectureDto(l, reportList);
  }

  /**
   * 全講義データを取得します。
   * 
   * @return 全講義データ
   */
  LectureDto[] getAllLectures() {
    final LectureDao lectureDao = this.lectureDaoFactory.create();
    final List<Lecture> lectures = lectureDao.getAllLectures();
    lectureDao.close();

    final LectureDto[] lectureDtoList = new LectureDto[lectures.size()];
    final ReportDao reportDao = this.reportDaoFactory.create();
    for (int i = 0; i < lectures.size(); i++) {
      final Lecture lecture = lectures.get(i);
      final List<Report> reportList = reportDao.getReportsFromLectureId(lecture.getLectureId());

      lectureDtoList[i] = new LectureDto(lecture, reportList);
    }
    reportDao.close();
    return lectureDtoList;
  }

  /**
   * 講義数を取得します。
   * 
   * @return 講義数
   */
  int getLectureCount() {
    final LectureDao dao = this.lectureDaoFactory.create();
    final int lectureCount = dao.getLectureCount();
    dao.close();
    return lectureCount;
  }
}
