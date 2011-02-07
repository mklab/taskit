/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Report;
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

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getEvaluation(java.lang.String,
   *      int, int)
   */
  @SuppressWarnings("boxing")
  @Override
  public int getEvaluation(String useName, int lectureId, int no) {
    Query q1 = this.entityManager.createQuery("SELECT r.reportId FROM REPORT r WHERE r.lectureId = :lectureId"); //$NON-NLS-1$
    q1.setParameter("lectureId", lectureId); //$NON-NLS-1$
    int reportId = (Integer)q1.getSingleResult();
    Query query = this.entityManager.createQuery("SELECT s.evaluation FROM SUBMISSION s WHERE s.studentNo = :userName AND s.reportId = :reportId AND s.no = :no"); //$NON-NLS-1$
    query.setParameter("userName", useName); //$NON-NLS-1$
    query.setParameter("reportId", reportId); //$NON-NLS-1$
    query.setParameter("no", no); //$NON-NLS-1$
    return (Integer)query.getSingleResult();
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registerReport(org.mklab.taskit.shared.model.Report)
   */
  @Override
  public void registerSubmission(Submission submission) throws Exception {
    EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(submission);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new Exception("submission already exists!"); //$NON-NLS-1$
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new Exception("failed to submission a report"); //$NON-NLS-1$
      } catch (Throwable e1) {
        throw new Exception("failed to submission a report, and rollback failed."); //$NON-NLS-1$
      }
    }
  }
}
