/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Lecture;


/**
 * @author teshima
 * @version $Revision$, Jan 31, 2011
 */
public class LectureDaoImpl implements LectureDao {

  /** エンティティマネージャです。 */
  private EntityManager entityManager;

  /**
   * {@link LectureDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public LectureDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#getAllLectures()
   */
  @Override
  public List<Lecture> getAllLectures() {
    final Query query = this.entityManager.createQuery("SELECT l FROM LECTURE l"); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    final List<Lecture> lectures = query.getResultList();
    return lectures;
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#registerLecture(org.mklab.taskit.shared.model.Lecture)
   */
  @Override
  public void registerLecture(Lecture lecture) {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(lecture);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
  }

}
