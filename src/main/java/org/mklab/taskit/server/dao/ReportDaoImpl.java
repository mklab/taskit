/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
   *      java.lang.String, java.lang.String, java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  @Override
  public void registReport(String id, String name, String detail, String date, String level, String allotment) throws ReportRegistrationException {
    // TODO Auto-generated method stub
    final Report report = new Report(id, name, detail, date, level, allotment);
    EntityTransaction t = this.entityManager.getTransaction();
    t.begin();
    try {
      this.entityManager.persist(report);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new ReportRegistrationException("report already exists!"); //$NON-NLS-1$
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new ReportRegistrationException("failed to register a report"); //$NON-NLS-1$
      } catch (Throwable e1) {
        throw new ReportRegistrationException("failed to register a report, and rollback failed."); //$NON-NLS-1$
      }
    }
  }

}
