package org.mklab.taskit.server;

import org.junit.Test;
import org.mklab.taskit.shared.SubmissionRequest;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class SubmissionRequestTest {

  @Test
  public void testSubmit() {
    SubmissionRequest req = RequestFactoryUtil.requestFactory().submissionRequest();
    req.submit("10675003", 1, 100).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void response) {
        System.out.println("Success");
      }

    });
    req = RequestFactoryUtil.requestFactory().submissionRequest();
    req.submit("10675003", 1, 100).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void response) {
        System.out.println("Success");
      }

    });
  }
}
