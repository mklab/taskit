/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Submission;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public class SubmissionDaoImpl implements SubmissionDao {

  private EntityManager entityManager;

  /**
   * {@link SubmissionDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャー
   */
  public SubmissionDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.SubmissionDao#getAllSubmission(java.lang.String)
   */
  @Override
  public List<Submission> getAllSubmission(String studentNo) {
    Query query = this.entityManager.createQuery("select s from SUBMISSION as s where studentNo = '" + studentNo + "'"); //$NON-NLS-1$ //$NON-NLS-2$
    @SuppressWarnings({"cast", "unchecked"})
    List<Submission> submissions = (List<Submission>)query.getResultList();

    return submissions;
  }

}
