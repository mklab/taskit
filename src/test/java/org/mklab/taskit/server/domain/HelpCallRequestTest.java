/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.HelpCallRequest;
import org.mklab.taskit.shared.UserType;

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

  /**
   * ヘルプコールの呼び出し順序取得のテストを行います。
   */
  @SuppressWarnings("nls")
  @Test
  public void testGetPosition() {
    Account.registerNewAccount("s1", "", UserType.STUDENT);
    Account.registerNewAccount("s2", "", UserType.STUDENT);

    final User student1 = User.getUserByAccountId("s1");
    final User student2 = User.getUserByAccountId("s2");

    login(student1);
    getRequestFactory().helpCallRequest().call(null).fire();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    login(student2);
    getRequestFactory().helpCallRequest().call(null).fire();

    login(student1);
    getRequestFactory().helpCallRequest().getPosition().fire(new Receiver<Long>() {

      @Override
      public void onSuccess(Long response) {
        assertEquals(0l, response.longValue());
      }
    });
    login(student2);
    getRequestFactory().helpCallRequest().getPosition().fire(new Receiver<Long>() {

      @Override
      public void onSuccess(Long response) {
        assertEquals(1l, response.longValue());
      }
    });
  }
}
