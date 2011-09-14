/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.AttendanceType;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceTypeDaoImpl extends AbstractDao implements AttendanceTypeDao {

  /**
   * {@link AttendanceTypeDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AttendanceTypeDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceTypeDao#registerAttendanceType(org.mklab.taskit.shared.model.AttendanceType)
   */
  @Override
  public void registerAttendanceType(AttendanceType attendanceType) {
    final EntityManager entityManager = entityManager();
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();
    try {
      entityManager.persist(attendanceType);
      t.commit();
    } catch (IllegalStateException e) {
      t.rollback();
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceTypeDao#getAllAttendanceTypes()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<String> getAllAttendanceTypes() {
    final Query query = entityManager().createQuery("SELECT t.type FROM ATTENDANCE_TYPE t"); //$NON-NLS-1$
    return query.getResultList();
  }

}
