package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;

import java.util.List;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * {@link SubmissionRequest}のテストケースです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class SubmissionRequestTest extends DomainTest {

  /**
   * 提出が正常に行われるかどうかテストします。
   */
  @Test
  public void testPersist() {
    loginAsTeacher();
    final Lectures lectures = new Lectures();
    lectures.initialize(getRequestFactory());

    registerSingleSubmission(lectures);
    {
      SubmissionRequest req = getRequestFactory().submissionRequest();
      req.getSubmissionsByAccountId(STUDENT_PROXY.getAccount().getId()).fire(new Receiver<List<SubmissionProxy>>() {

        @Override
        public void onSuccess(List<SubmissionProxy> response) {
          assertEquals(1, response.size());
        }

      });
    }
  }

  /**
   * アカウントとレポートのIDの組み合わせが重複する提出がなされた場合に例外が発生するかどうかテストします。
   */
  @Test
  public void testPersistDuplicatePair() {
    loginAsTeacher();
    final Lectures lectures = new Lectures();
    lectures.initialize(getRequestFactory());

    registerSingleSubmission(lectures);
    boolean thrown = false;
    try {
      registerSingleSubmission(lectures);
    } catch (RuntimeException ex) {
      assertEquals("Server Error: already submitted.", ex.getCause().getMessage()); //$NON-NLS-1$
      thrown = true;
    }

    assertTrue(thrown);
  }

  private static void registerSingleSubmission(final Lectures lectures) {
    loginAsTA();
    {
      SubmissionRequest req = getRequestFactory().submissionRequest();
      final SubmissionProxy submission = req.create(SubmissionProxy.class);
      submission.setPoint(100);
      submission.setSubmitter(STUDENT_PROXY.getAccount());
      submission.setReport(lectures.lecture1_report1);
      req.persist().using(submission).fire(new Receiver<Void>() {

        @Override
        public void onSuccess(@SuppressWarnings("unused") Void response) {
          // do nothing
        }

      });
    }
  }

}
