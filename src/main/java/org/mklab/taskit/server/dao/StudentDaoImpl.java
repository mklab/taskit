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
    return studentNo;
  }

  /**
   * @see org.mklab.taskit.server.dao.StudentDao#getAllStudentsSubmissionFromLectureId(int)
   */
  @Override
  public List<Submission> getAllStudentsSubmissionFromLectureId(int lectureId) {
    // TODO 未実装です。
    return null;
  }

}
