package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AccountRequest;
import org.mklab.taskit.shared.UserType;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * {@link AccountRequest}のテストを行うクラスです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class AccountRequestTest extends DomainTest {

  /**
   * 登録を行い、そのユーザーが本当に登録できているかどうかテストします。
   */
  @Test
  public void testRegisterNewAccount() {
    loginAsTeacher();
    // register
    {
      AccountRequest req = getRequestFactory().accountRequest();
      req.registerNewAccount("10675003", "hoge", UserType.TEACHER).fire(new Receiver<Void>() { //$NON-NLS-1$ //$NON-NLS-2$

            @Override
            public void onSuccess(@SuppressWarnings("unused") Void arg0) {
              // do nothing
            }

          });
    }
    // get unregistered user
    {
      AccountRequest req = getRequestFactory().accountRequest();
      req.getAccountById("aaaa").fire(new Receiver<AccountProxy>() { //$NON-NLS-1$

            @Override
            public void onSuccess(AccountProxy response) {
              assertNull(response);
            }

          });
    }
    // get registered user
    {
      AccountRequest req = getRequestFactory().accountRequest();
      req.getAccountById("10675003").fire(new Receiver<AccountProxy>() { //$NON-NLS-1$

            @Override
            public void onSuccess(AccountProxy response) {
              assertNotNull(response);
            }

          });
    }
  }

  /**
   * 同じIDを登録しようとした場合に例外をスローするかテストします。
   */
  @SuppressWarnings("nls")
  @Test
  public void testRegisterDuplicatedAccount() {
    loginAsTA();

    AccountRequest req = getRequestFactory().accountRequest();
    req.registerNewAccount("10675003", "hoge", UserType.TEACHER).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        fail();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(@SuppressWarnings("unused") ServerFailure error) {
        // do nothing
      }

    });
  }

}
