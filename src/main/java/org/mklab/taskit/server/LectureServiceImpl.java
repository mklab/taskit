/**
 * 
 */
package org.mklab.taskit.server;

import javax.persistence.EntityManager;

import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.service.LectureService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class LectureServiceImpl extends TaskitRemoteService implements LectureService {

  /** for serialization. */
  private static final long serialVersionUID = 3530442985606745638L;
  private LectureQuery query;

  /**
   * {@link LectureServiceImpl}オブジェクトを構築します。
   */
  public LectureServiceImpl() {
    final EntityManager entityManager = createEntityManager();
    this.query = new LectureQuery(entityManager);
  }

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

}
