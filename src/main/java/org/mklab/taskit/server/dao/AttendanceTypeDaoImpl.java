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
public class AttendanceTypeDaoImpl implements AttendanceTypeDao {

  /** エンティティマネージャ */
  private EntityManager entityManager;

  /**
   * {@link AttendanceTypeDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AttendanceTypeDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceTypeDao#registerAttendanceType(org.mklab.taskit.shared.model.AttendanceType)
   */
  @Override
  public void registerAttendanceType(AttendanceType attendanceType) {
    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(attendanceType);
      t.commit();
    } catch (IllegalStateException e) {
      t.rollback();
    }
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceTypeDao#getAllAttendanceTypes()
   */
  @Override
  public List<String> getAllAttendanceTypes() {
    final Query query = this.entityManager.createQuery("SELECT t.type FROM ATTENDANCE_TYPE t"); //$NON-NLS-1$
    return query.getResultList();
  }

}
