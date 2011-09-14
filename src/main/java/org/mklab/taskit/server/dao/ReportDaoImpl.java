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
public class ReportDaoImpl extends AbstractDao implements ReportDao {

  /**
   * {@link ReportDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public ReportDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportFromDate(java.lang.String)
   */
  @Override
  public List<Report> getReportFromDate(String date) {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT r FROM Report r WHERE r.date = :date"); //$NON-NLS-1$
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
    final EntityManager entityManager = entityManager();
    return entityManager.find(Report.class, Integer.valueOf(id));
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getReportsFromLectureId(int)
   */
  @SuppressWarnings({"boxing", "unchecked"})
  @Override
  public List<Report> getReportsFromLectureId(int lectureId) {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT r FROM REPORT r WHERE r.lectureId = :lectureId"); //$NON-NLS-1$
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
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT r FROM REPORT r ORDER BY r.lectureId, r.no"); //$NON-NLS-1$
    return query.getResultList();
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#registerReport(org.mklab.taskit.shared.model.Report)
   */
  @Override
  public void registerReport(Report report) throws ReportRegistrationException {
    final EntityManager entityManager = entityManager();
    EntityTransaction t = entityManager.getTransaction();
    t.begin();
    try {
      entityManager.persist(report);
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
   * @see org.mklab.taskit.server.dao.ReportDao#getTitle(int, int)
   */
  @SuppressWarnings("boxing")
  @Override
  public String getTitle(int lectureId, int no) {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT r.title FROM REPORT r WHERE r.lectureId = :lectureId AND r.no = :no"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    query.setParameter("no", no); //$NON-NLS-1$
    final String title = (String)query.getSingleResult();
    return title;
  }

  /**
   * @see org.mklab.taskit.server.dao.ReportDao#getDetail(int, int)
   */
  @SuppressWarnings("boxing")
  @Override
  public String getDetail(int lectureId, int no) {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT r.detail FROM REPORT r WHERE r.lectureId = lectureId AND r.no = :no"); //$NON-NLS-1$
    query.setParameter("lectureId", lectureId); //$NON-NLS-1$
    query.setParameter("no", no); //$NON-NLS-1$
    return null;
  }

}
