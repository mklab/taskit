/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
   * @see org.mklab.taskit.server.dao.SubmissionDao#getSubmissionsFromUserName(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Submission> getSubmissionsFromUserName(String userName) {
    Query query = this.entityManager.createQuery("SELECT s FROM SUBMISSION s WHERE s.userName = :userName ORDER BY s.reportId"); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    List<Submission> submissions = query.getResultList();

    return submissions;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registerReport(org.mklab.taskit.shared.model.Report)
   */
  @Override
  public void registerSubmission(Submission submission) throws SubmissionRegistrationException {
    EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(submission);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new SubmissionRegistrationException("submission already exists!"); //$NON-NLS-1$
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new SubmissionRegistrationException("failed to submission a report"); //$NON-NLS-1$
      } catch (Throwable e1) {
        throw new SubmissionRegistrationException("failed to submission a report, and rollback failed."); //$NON-NLS-1$
      }
    }
  }

  /**
   * @throws SubmissionRegistrationException
   * @see org.mklab.taskit.server.dao.SubmissionDao#setEvaluation(String, int,
   *      int, int, String, String)
   */
  @SuppressWarnings("boxing")
  @Override
  public void setEvaluation(String userName, int reportId, int evaluation, int evaluatorId, String publicComment, String privateComment) throws SubmissionRegistrationException {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    final Query query = this.entityManager.createQuery("UPDATE SUBMISSION s SET s.evaluation = :evaluation, s.evaluatorId = :evaluatorId WHERE s.userName = :userName AND s.reportId = :reportId"); //$NON-NLS-1$
    query.setParameter("evaluation", evaluation); //$NON-NLS-1$
    query.setParameter("evaluatorId", evaluatorId); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    int executedCount = 0;
    try {
      query.executeUpdate();
      t.commit();
    } catch (Throwable e) {
      t.rollback();

    }
    if (executedCount == 0) {
      registerSubmission(new Submission(reportId, System.currentTimeMillis(), userName, evaluation, evaluatorId, publicComment, privateComment));
    }

  }

  /**
   * @see org.mklab.taskit.server.dao.SubmissionDao#getEvaluationFromReportId(String,
   *      int)
   */
  @SuppressWarnings("boxing")
  @Override
  public int getEvaluationFromReportId(String userName, int reportId) {
    final Query query = this.entityManager.createQuery("SELECT s.evaluation FROM SUBMISSION s WHERE s.userName = :userName AND s.reportId = :reportId"); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    return (Integer)query.getSingleResult();
  }
}
