/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Report;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
class ReportDaoImpl implements ReportDao {

  private EntityManager entityManager;

  ReportDaoImpl(EntityManager entityManager) {
    if (entityManager == null) throw new NullPointerException();
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportWithID(java.lang.String)
   */
  @Override
  public Report getReportWithID(String id) {

    Query query = this.entityManager.createQuery("SELECT r.name, r.detail, r.level, r.allotment FROM Report r WHERE r.id = :id");
    query.setParameter("id", id);
    Object[] objects = (Object[])query.getSingleResult();
    String name = (String)objects[0];
    String detail = (String)objects[1];
    String date = (String)objects[2];
    String level = (String)objects[3];
    String allotment = (String)objects[4];
    Report report = new Report(id, name, detail, date, level, allotment);
    return report;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportWithDateAndLevel(java.lang.String,
   *      String)
   */
  @Override
  public Report getReportWithDateAndLevel(String date, String level) {
    Query query = this.entityManager.createQuery("SELECT r.id, r.name, r.detail, r.allotment FROM Report r WHERE r.date = :date AND r.level = :level");
    query.setParameter("date", date);
    query.setParameter("level", level);
    Object[] objects = (Object[])query.getSingleResult();
    String id = (String)objects[0];
    String name = (String)objects[1];
    String detail = (String)objects[2];
    String allotment = (String)objects[3];
    Report report = new Report(id, name, detail, date, level, allotment);
    return report;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registReport(java.lang.String,
   *      java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void registReport(String id, String name, String date, String allotment) {
    // TODO Auto-generated method stub

  }

}
