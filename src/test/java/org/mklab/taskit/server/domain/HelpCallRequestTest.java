/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.mklab.taskit.shared.HelpCallListItemProxy;
import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.HelpCallRequest;
import org.mklab.taskit.shared.UserType;

import java.util.List;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class HelpCallRequestTest extends DomainTest {

  /**
   * 呼び出し情報が記録されるかどうかのテストを行います。
   */
  @SuppressWarnings("nls")
  @Test
  public void testCall() {
    final User s1 = registerUser("student1", "student1", UserType.STUDENT);
    final User s2 = registerUser("student2", "student2", UserType.STUDENT);
    final User s3 = registerUser("student3", "student3", UserType.STUDENT);

    HelpCallRequest req = getRequestFactory().helpCallRequest();
    login(s1);
    req.call(null).fire();

    req = getRequestFactory().helpCallRequest();
    login(s2);
    req.call(null).fire();

    req = getRequestFactory().helpCallRequest();
    login(s3);
    req.call(null).fire();

    loginAsTeacher();
    req = getRequestFactory().helpCallRequest();
    req.getAllHelpCalls().with("caller").fire(new Receiver<List<HelpCallProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<HelpCallProxy> calls) {
            assertEquals(3, calls.size());
            HelpCallProxy call1 = calls.get(0);
            assertEquals(s1.getAccount().getId(), call1.getCaller().getId());

            HelpCallProxy call2 = calls.get(1);
            assertEquals(s2.getAccount().getId(), call2.getCaller().getId());

            HelpCallProxy call3 = calls.get(2);
            assertEquals(s3.getAccount().getId(), call3.getCaller().getId());
          }

        });
  }

  @SuppressWarnings("nls")
  @Test
  public void testGetAllHelpCallListItems() {
    loginAsTeacher();
    User s1 = registerUser("student1", "student1", UserType.STUDENT);
    User s2 = registerUser("student2", "student2", UserType.STUDENT);
    User s3 = registerUser("student3", "student3", UserType.STUDENT);

    HelpCallRequest req = getRequestFactory().helpCallRequest();
    login(s1);
    req.call(null).fire();

    login(s2);
    req = getRequestFactory().helpCallRequest();
    req.call(null).fire();

    login(s3);
    req = getRequestFactory().helpCallRequest();
    req.call(null).fire();

    loginAsTA();
    req = getRequestFactory().helpCallRequest();
    req.getHelpCallListItems().with("helpCall.caller").fire(new Receiver<List<HelpCallListItemProxy>>() {

      @Override
      public void onSuccess(List<HelpCallListItemProxy> response) {
        for (HelpCallListItemProxy item : response) {
          assertNotNull("caller == null!!!", item.getHelpCall().getCaller());
        }
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
