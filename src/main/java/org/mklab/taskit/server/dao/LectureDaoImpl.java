/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
   * @see org.mklab.taskit.server.dao.LectureDao#getTitleFromDate(java.lang.String)
   */
  @Override
  public String getTitleFromDate(String date) {
    final Query query = this.entityManager.createQuery("SELECT l.title FROM LECTURE l WHERE l.date = :date"); //$NON-NLS-1$
    query.setParameter("date", date); //$NON-NLS-1$
    final List<String> titleList = query.getResultList();
    if (titleList.size() == 0) return null;
    if (titleList.size() > 1) throw new IllegalStateException();

    return titleList.get(0);
  }

  /**
   * @see org.mklab.taskit.server.dao.LectureDao#getAllLectures()
   */
  @Override
  public List<Lecture> getAllLectures() {
    final Query query = this.entityManager.createQuery("SELECT l FROM LECTURE l"); //$NON-NLS-1$
    List<Lecture> lectures = query.getResultList();
    return lectures;
  }

}
