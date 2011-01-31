/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Attendance;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public class AttendanceDaoImpl implements AttendanceDao {

  private EntityManager entityManager;

  public AttendanceDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.AttendanceDao#getAttendanceState(java.lang.String)
   */
  @Override
  public List<Attendance> getAttendanceState(String studentNo) {
    Query query = this.entityManager.createQuery("select s from ATTENDANCE as s where studentNo = '" + studentNo + "'");
    List<Attendance> attendances = (List<Attendance>)query.getSingleResult();
    
    return attendances;
  }

}
