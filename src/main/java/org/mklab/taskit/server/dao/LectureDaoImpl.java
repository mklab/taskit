/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


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
  public String getTitleFromDate(String date) throws NoResultException {

    Query query = this.entityManager.createQuery("SELECT l.title FROM LECTURE l WHERE l.date = :date"); //$NON-NLS-1$
    query.setParameter("date", date); //$NON-NLS-1$
    String title = (String)query.getSingleResult();
    return title;
  }

}
