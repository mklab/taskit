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
 * @version $Revision$, Jan 28, 2011
 */
public class StudentDaoImpl implements StudentDao {

  private EntityManager entityManager;

  /**
   * {@link StudentDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager
   */
  StudentDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.StudentDao#getStudentNo(java.lang.String)
   */
  @Override
  public String getStudentNo(String accountId) {
    Query query = this.entityManager.createQuery("select accountId from STUDENT where accountId = " + accountId); //$NON-NLS-1$
    String studentNo = (String)query.getSingleResult();
    this.entityManager.close();
    return studentNo;
  }

  /**
   * @see org.mklab.taskit.server.dao.StudentDao#getAllStudentsSubmissionFromLectureId(int)
   */
  @SuppressWarnings("boxing")
  @Override
  public List<Submission> getAllStudentsSubmissionFromLectureId(int lectureId) {
    Query query = this.entityManager.createQuery("SELECT s FROM SUBMISSION s WHERE s.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    this.entityManager.close();
    return null;
  }

}
