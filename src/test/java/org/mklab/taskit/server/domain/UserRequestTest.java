/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserRequest;
import org.mklab.taskit.shared.model.UserType;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class UserRequestTest extends DomainTest {

  /**
   * {@link UserRequest#getLoginUser()}のテストを行います。
   */
  @Test
  public void testGetLoginUser() {
    getRequestFactory().userRequest().getLoginUser().fire(new Receiver<UserProxy>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") UserProxy arg0) {
        fail();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        assertEquals("Server Error: Not logged in.", error.getMessage()); //$NON-NLS-1$
      }

    });

    final User loginUser = loginAsStudent();
    getRequestFactory().userRequest().getLoginUser().with("account").fire(new Receiver<UserProxy>() { //$NON-NLS-1$

          @Override
          public void onSuccess(UserProxy arg0) {
            assertNotNull(arg0);
            assertEquals(loginUser.getAccount().getId(), arg0.getAccount().getId());
          }
        });
  }

  /**
   * {@link UserRequest#getAllStudents()}のテストを行います。
   */
  @Test
  public void testGetAllStudents() {
    loginAsTeacher();
    for (int i = 0; i < 10; i++) {
      registerUser(String.valueOf((int)(Math.random() * 10000000) + i), "hoge", UserType.STUDENT); //$NON-NLS-1$
    }

    loginAsTA();

    getRequestFactory().userRequest().getAllStudents().with("account").fire(new Receiver<List<UserProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<UserProxy> arg0) {
            String prev = null;
            for (UserProxy user : arg0) {
              if (prev != null) {
                assertTrue(prev.compareTo(user.getAccount().getId()) < 0);
              }
              assertEquals(UserType.STUDENT, user.getType());
              prev = user.getAccount().getId();
            }
          }

        });
  }

}
