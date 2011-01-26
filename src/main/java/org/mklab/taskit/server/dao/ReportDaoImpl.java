/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Report;


/**
 * @author teshima
 * @version $Revision$, Jan 26, 2011
 */
class ReportDaoImpl implements ReportDao {

  private EntityManager entityManager;

  ReportDaoImpl(EntityManager entityManager) {
    if (entityManager == null) throw new NullPointerException();
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromDateAndLevel(java.lang.String,
   *      String)
   */
  @Override
  public Report getReportFromDate(String date) {
    Query query = this.entityManager.createQuery("SELECT r.id, r.name, r.detail, r.level, r.allotment FROM Report r WHERE r.date = :date"); //$NON-NLS-1$
    query.setParameter("date", date); //$NON-NLS-1$
    Object[] objects = (Object[])query.getSingleResult();
    String id = (String)objects[0];
    String name = (String)objects[1];
    String detail = (String)objects[2];
    String level = (String)objects[3];
    String allotment = (String)objects[4];
    Report report = new Report(id, name, detail, date, level, allotment);
    return report;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromID(java.lang.String)
   */
  @Override
  public Report getReportFromID(String id) {

    Query query = this.entityManager.createQuery("SELECT r.name, r.detail, r.level, r.allotment FROM Report r WHERE r.id = :id"); //$NON-NLS-1$
    query.setParameter("id", id); //$NON-NLS-1$
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
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromDateAndLevel(java.lang.String,
   *      String)
   */
  @Override
  public Report getReportFromDateAndLevel(String date, String level) {
    Query query = this.entityManager.createQuery("SELECT r.id, r.name, r.detail, r.allotment FROM Report r WHERE r.date = :date AND r.level = :level"); //$NON-NLS-1$
    query.setParameter("date", date); //$NON-NLS-1$
    query.setParameter("level", level); //$NON-NLS-1$
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
