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
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromDate(java.lang.String)
   */
  @Override
  public List<Report> getReportFromDate(String date) {
    final Query query = this.entityManager.createQuery("SELECT r FROM Report r WHERE r.date = :date"); //$NON-NLS-1$
    query.setParameter("date", date); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    final List<Report> reportList = query.getResultList();
    if (reportList.size() == 0) return null;
    if (reportList.size() > 1) throw new IllegalStateException();
    return reportList;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromID(int)
   */
  @Override
  public Report getReportFromID(int id) {
    return this.entityManager.find(Report.class, Integer.valueOf(id));
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registerReport(org.mklab.taskit.shared.model.Report)
   */
  @Override
  public void registerReport(Report report) throws ReportRegistrationException {
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
