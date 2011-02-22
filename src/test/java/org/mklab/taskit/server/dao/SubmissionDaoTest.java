/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
   * 
   * @throws ReportRegistrationException 課題登録失敗の際の例外
   * @throws SubmissionRegistrationException 提出物登録失敗の際の例外
   */
  @Test
  public void testGetEvaluation() throws ReportRegistrationException, SubmissionRegistrationException {
    final ReportDao reportDao = new ReportDaoImpl(createEntityManager());
    reportDao.registerReport(new Report(0, "Hello World.", "detail", 1, 0)); //$NON-NLS-1$//$NON-NLS-2$
    reportDao.registerReport(new Report(1, "Show your name.", "detail", 2, 0)); //$NON-NLS-1$ //$NON-NLS-2$
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236002", 60, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456800, "10236002", 65, 2, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    assertEquals(70, submissionDao.getEvaluationFromReportId("10236001", 0)); //$NON-NLS-1$
    assertEquals(80, submissionDao.getEvaluationFromReportId("10236001", 1)); //$NON-NLS-1$
    assertEquals(60, submissionDao.getEvaluationFromReportId("10236002", 0)); //$NON-NLS-1$
    assertEquals(65, submissionDao.getEvaluationFromReportId("10236002", 1)); //$NON-NLS-1$

  }

  /**
   * 誰々の第何回目の何番目の課題の成績を修正します。
   * 
   * @throws ReportRegistrationException 課題登録失敗の例外
   * @throws SubmissionRegistrationException 提出物登録失敗の例外
   */
  @Test
  public void testSetEvaluation() throws ReportRegistrationException, SubmissionRegistrationException {
    final ReportDao reportDao = new ReportDaoImpl(createEntityManager());
    reportDao.registerReport(new Report(0, "Hello World.", "detail", 1, 0)); //$NON-NLS-1$//$NON-NLS-2$
    reportDao.registerReport(new Report(1, "Show your name.", "detail", 2, 0)); //$NON-NLS-1$ //$NON-NLS-2$
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236002", 60, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456800, "10236002", 65, 2, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.setEvaluation("10236001", 0, 80, 1, null, null); //$NON-NLS-1$
    assertEquals(80, submissionDao.getEvaluationFromReportId("10236001", 0)); //$NON-NLS-1$
    submissionDao.setEvaluation("10236002", 1, 100, 3, null, null); //$NON-NLS-1$
    assertEquals(100, submissionDao.getEvaluationFromReportId("10236002", 1)); //$NON-NLS-1$

  }

  /**
   * ある学生の全ての提出物を取得できているかどうかテストします。
   * 
   * @throws SubmissionRegistrationException 提出物登録失敗の例外
   */
  @Test
  public void testGetSubmissionsFromUserName() throws SubmissionRegistrationException {
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236002", 60, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456800, "10236002", 65, 2, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(3, 123456999, "10236001", 85, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(2, 123456900, "10236001", 90, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    List<Submission> submissions = submissionDao.getSubmissionsFromUserName("10236001"); //$NON-NLS-1$
    assertEquals(4, submissions.size());
    assertEquals(70, submissions.get(0).getEvaluation());
    assertEquals(80, submissions.get(1).getEvaluation());
    assertEquals(90, submissions.get(2).getEvaluation());
    assertEquals(85, submissions.get(3).getEvaluation());
  }

  /**
   * 提出物の登録用のメソッドのテストです。
   * 
   * @throws SubmissionRegistrationException
   */
  @Test
  public void testRegisterSubmission() throws SubmissionRegistrationException {
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    //submissionDao.registerSubmission(new Submission(0, 123456889, "10236001", 60, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(1, 123456889, "10236001", 80, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(2, 123456999, "10236001", 85, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(3, 123456900, "10236001", 90, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

    List<Submission> submissions1 = submissionDao.getSubmissionsFromUserName("10236001"); //$NON-NLS-1$
    assertEquals(4, submissions1.size());
    assertEquals(70, submissions1.get(0).getEvaluation());
    assertEquals(80, submissions1.get(1).getEvaluation());
    assertEquals(85, submissions1.get(2).getEvaluation());
    assertEquals(90, submissions1.get(3).getEvaluation());

  }

  /**
   * 提出物登録時に、すでに登録されている提出物の場合に例外がスローされるかどうかテストします。
   * 
   * @throws SubmissionRegistrationException 重複した提出物が登録された場合
   */
  @Test(expected = SubmissionRegistrationException.class)
  public void testRegisterDuplicatedSubmission() throws SubmissionRegistrationException {
    final SubmissionDao submissionDao = new SubmissionDaoImpl(createEntityManager());
    submissionDao.registerSubmission(new Submission(0, 123456789, "10236001", 70, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    submissionDao.registerSubmission(new Submission(0, 123456889, "10236001", 60, 1, "public comment", "private comment")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  }
}
