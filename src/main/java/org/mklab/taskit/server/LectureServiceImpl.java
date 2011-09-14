/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.server.dao.LectureDao;
import org.mklab.taskit.server.dao.LectureDaoFactory;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.service.LectureService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class LectureServiceImpl extends TaskitRemoteService implements LectureService {

  /** for serialization. */
  private static final long serialVersionUID = 3530442985606745638L;
  private LectureQuery query = new LectureQuery();
  private LectureDaoFactory lectureDaoFactory = new LectureDaoFactory();

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getLecture(int)
   */
  @Override
  public LectureDto getLecture(int index) {
    return this.query.getLecture(index);
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getAllLectures()
   */
  @Override
  public LectureDto[] getAllLectures() {
    return this.query.getAllLectures();
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#getLectureCount()
   */
  @Override
  public int getLectureCount() {
    return this.query.getLectureCount();
  }

  /**
   * @see org.mklab.taskit.shared.service.LectureService#createNewLecture(java.lang.String,
   *      long)
   */
  @Override
  public void createNewLecture(String title, long date) {
    SessionUtil.assertIsTeacher(getSession());

    final LectureDao lectureDao = this.lectureDaoFactory.create();
    lectureDao.registerLecture(new Lecture(title, date));
    lectureDao.close();
  }

}
