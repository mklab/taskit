package org.mklab.taskit.server;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * {@link SubmissionRequest}のテストケースです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class SubmissionRequestTest {

  /**
   * 提出が正常に行われるかどうかテストします。
   */
  @Test
  @SuppressWarnings("unused")
  public void testSubmit() {
    SubmissionRequest req = RequestFactoryUtil.requestFactory().submissionRequest();
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

    req = RequestFactoryUtil.requestFactory().submissionRequest();
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
    SubmissionRequest req = RequestFactoryUtil.requestFactory().submissionRequest();
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
    req = RequestFactoryUtil.requestFactory().submissionRequest();
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
