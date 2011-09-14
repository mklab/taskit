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
public class LectureDaoImpl extends AbstractDao implements LectureDao {

  /**
   * {@link LectureDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public LectureDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#getAllLectures()
   */
  @Override
  public List<Lecture> getAllLectures() {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT l FROM LECTURE l ORDER BY l.time ASC"); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    final List<Lecture> lectures = query.getResultList();
    return lectures;
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#registerLecture(org.mklab.taskit.shared.model.Lecture)
   */
  @Override
  public void registerLecture(Lecture lecture) {
    final EntityManager entityManager = entityManager();
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();
    try {
      entityManager.persist(lecture);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#getLectureCount()
   */
  @Override
  public int getLectureCount() {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT count(l) FROM LECTURE l"); //$NON-NLS-1$

    final Long result = (Long)query.getSingleResult();
    return result.intValue();
  }

}
