/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.shared.HelpCallRequest;
import org.mklab.taskit.shared.model.UserType;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class HelpCallRequestTest {

  /**
   * 呼び出し情報が記録されるかどうかのテストを行います。
   */
  @Test
  public void testCall() {
    final HelpCallRequest req = RequestFactoryUtil.requestFactory().helpCallRequest();
    final UserType bak = RequestFactoryUtil.getUserType();

    RequestFactoryUtil.setUserType(UserType.STUDENT);
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
    RequestFactoryUtil.setUserType(bak);
  }

}
