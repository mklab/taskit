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
public class ReportDaoImpl implements ReportDao {

  private EntityManager entityManager;

  /**
   * {@link ReportDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public ReportDaoImpl(EntityManager entityManager) {
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

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportsFromLectureId(int)
   */
  @SuppressWarnings({"boxing", "unchecked"})
  @Override
  public List<Report> getReportsFromLectureId(int lectureId) {
    Query query = this.entityManager.createQuery("SELECT r FROM REPORT r WHERE r.lectureId = :lectureId"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    List<Report> selectedReports = query.getResultList();
    return selectedReports;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getAllReports()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Report> getAllReports() {
    final Query query = this.entityManager.createQuery("SELECT r FROM REPORT r ORDER BY r.lectureId, r.no"); //$NON-NLS-1$
    return query.getResultList();
  }

}
