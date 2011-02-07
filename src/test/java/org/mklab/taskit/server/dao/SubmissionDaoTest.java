/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.model.Submission;

/**
 * @author teshima
 * @version $Revision$, Feb 7, 2011
 */
public class SubmissionDaoTest extends DaoTest {

  /**
   * 誰々の第何回目の何番目の課題の成績を取得します。
   * @throws Exception 
   */
  @Test
  public void testGetEvaluation() throws Exception {
    final ReportDao reportDao = new ReportDaoImpl(createEntityManager());
    reportDao.registerReport(new Report(0, "Hello World.", "detail", 1, 0));  //$NON-NLS-1$//$NON-NLS-2$
    reportDao.registerReport(new Report(1, "Show your name.", "detail", 2, 0)); //$NON-NLS-1$ //$NON-NLS-2$
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236002", 60, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456800, "10236002", 65, 2, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    assertEquals(70, submissionDao.getEvaluationFromReportId("10236001", 0)); //$NON-NLS-1$
    assertEquals(80, submissionDao.getEvaluationFromReportId("10236001", 1)); //$NON-NLS-1$
    assertEquals(60, submissionDao.getEvaluationFromReportId("10236002", 0)); //$NON-NLS-1$
    assertEquals(65, submissionDao.getEvaluationFromReportId("10236002", 1)); //$NON-NLS-1$
    
  }
  /**
   * 誰々の第何回目の何番目の課題の成績を修正します。
   * @throws Exception
   */
  @Test
  public void testSetEvaluation() throws Exception {
    final ReportDao reportDao = new ReportDaoImpl(createEntityManager());
    reportDao.registerReport(new Report(0, "Hello World.", "detail", 1, 0));  //$NON-NLS-1$//$NON-NLS-2$
    reportDao.registerReport(new Report(1, "Show your name.", "detail", 2, 0)); //$NON-NLS-1$ //$NON-NLS-2$
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236002", 60, 1, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456800, "10236002", 65, 2, "public comment", "private comment"));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.setEvaluation("10236001", 0, 80); //$NON-NLS-1$
    assertEquals(80, submissionDao.getEvaluationFromReportId("10236001", 0)); //$NON-NLS-1$
    
  }

}
