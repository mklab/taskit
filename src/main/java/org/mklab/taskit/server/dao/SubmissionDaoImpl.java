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
public class SubmissionDaoImpl extends AbstractDao implements SubmissionDao {

  /**
   * {@link SubmissionDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャー
   */
  public SubmissionDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.SubmissionDao#getSubmissionsFromUserName(java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Submission> getSubmissionsFromUserName(String userName) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT s FROM SUBMISSION s WHERE s.userName = :userName ORDER BY s.reportId"); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    List<Submission> submissions = query.getResultList();
    return submissions;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registerReport(org.mklab.taskit.shared.model.Report)
   */
  @SuppressWarnings("boxing")
  @Override
  public void registerSubmission(Submission submission) throws SubmissionRegistrationException {
    String userName = submission.getUserName();
    int reportId = submission.getReportId();
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT s FROM SUBMISSION s WHERE s.userName = :userName AND s.reportId = :reportId"); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    if (!query.getResultList().isEmpty()) {
      throw new SubmissionRegistrationException("This submission has already registered"); //$NON-NLS-1$
    }
    EntityTransaction t = entityManager.getTransaction();
    t.begin();
    try {
      entityManager.persist(submission);
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
   * @see org.mklab.taskit.server.dao.SubmissionDao#setEvaluation(java.lang.String,
   *      int, int, int, java.lang.String, java.lang.String)
   */
  @SuppressWarnings("boxing")
  @Override
  public void setEvaluation(String userName, int reportId, int evaluation, int evaluatorId, String publicComment, String privateComment) throws SubmissionRegistrationException {
    final EntityManager entityManager = entityManager();
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();
    final Query query = entityManager.createQuery("UPDATE SUBMISSION s SET s.evaluation = :evaluation, s.evaluatorId = :evaluatorId WHERE s.userName = :userName AND s.reportId = :reportId"); //$NON-NLS-1$
    query.setParameter("evaluation", evaluation); //$NON-NLS-1$
    query.setParameter("evaluatorId", evaluatorId); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    int executedCount = 0;
    try {
      executedCount = query.executeUpdate();
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
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT s.evaluation FROM SUBMISSION s WHERE s.userName = :userName AND s.reportId = :reportId"); //$NON-NLS-1$
    query.setParameter("userName", userName); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    return (Integer)query.getSingleResult();
  }
}
