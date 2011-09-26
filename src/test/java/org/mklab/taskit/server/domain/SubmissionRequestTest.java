package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;

import java.util.List;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


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
  @SuppressWarnings("unused")
  public void testSubmit() {
    loginAsTA();
    SubmissionRequest req = getRequestFactory().submissionRequest();
    final String id = "qeqrjzklf"; //$NON-NLS-1$
    final Integer reportId = Integer.valueOf(1);

    req.submit(id, reportId, 100).fire(new Receiver<SubmissionProxy>() {

      @Override
      public void onSuccess(SubmissionProxy response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        fail(error.getMessage());
      }

    });

    req = getRequestFactory().submissionRequest();
    req.getAllSubmissionsByAccountId(id).fire(new Receiver<List<SubmissionProxy>>() {

      @Override
      public void onSuccess(List<SubmissionProxy> response) {
        assertEquals(1, response.size());
        final SubmissionProxy s = response.get(0);
        assertEquals(id, s.getAccountId());
        assertEquals(reportId, s.getReportId());
      }

    });
  }

  /**
   * アカウントとレポートのIDの組み合わせが重複する提出がなされた場合に例外が発生するかどうかテストします。
   */
  @Test
  @SuppressWarnings({"unused", "nls"})
  public void testSubmitDuplicatePair() {
    loginAsTA();

    SubmissionRequest req = getRequestFactory().submissionRequest();
    final String id = "asdfasfdasdf";
    final Integer reportId = Integer.valueOf(1);
    req.submit(id, reportId, 100).fire(new Receiver<SubmissionProxy>() {

      @Override
      public void onSuccess(SubmissionProxy response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        fail();
      }

    });
    req = getRequestFactory().submissionRequest();
    req.submit(id, reportId, 100).fire(new Receiver<SubmissionProxy>() {

      @Override
      public void onSuccess(SubmissionProxy response) {
        fail();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        // do nothing
      }

    });
  }
}
