/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.HelpCallRequest;

import java.util.List;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class HelpCallRequestTest extends DomainTest {

  /**
   * 呼び出し情報が記録されるかどうかのテストを行います。
   */
  @Test
  public void testCall() {
    HelpCallRequest req = getRequestFactory().helpCallRequest();
    final User caller = loginAsStudent();

    req.call(null).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void arg0) {
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

    loginAsTeacher();
    req = getRequestFactory().helpCallRequest();
    req.getAllHelpCalls().with("caller").fire(new Receiver<List<HelpCallProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<HelpCallProxy> calls) {
            assertEquals(1, calls.size());
            assertEquals(caller.getAccount().getId(), calls.get(0).getCaller().getId());
          }

        });
  }
}
