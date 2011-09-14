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
public class StudentDaoImpl extends AbstractDao implements StudentDao {

  /**
   * {@link StudentDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager
   */
  StudentDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.StudentDao#getStudentNo(java.lang.String)
   */
  @Override
  public String getStudentNo(String accountId) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("select accountId from STUDENT where accountId = " + accountId); //$NON-NLS-1$
    String studentNo = (String)query.getSingleResult();
    return studentNo;
  }

  /**
   * @see org.mklab.taskit.server.dao.StudentDao#getAllStudentsSubmissionFromLectureId(int)
   */
  @SuppressWarnings("boxing")
  @Override
  public List<Submission> getAllStudentsSubmissionFromLectureId(int lectureId) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT s FROM SUBMISSION s WHERE s.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    return null;
  }

}
