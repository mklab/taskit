package org.mklab.taskit.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AccountRequest;
import org.mklab.taskit.shared.model.UserType;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * {@link AccountRequest}のテストを行うクラスです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class AccountRequestTest {

  /**
   * 登録を行い、そのユーザーが本当に登録できているかどうかテストします。
   */
  @Test
  public void testRegisterNewAccount() {
    // register
    {
      AccountRequest req = RequestFactoryUtil.requestFactory().accountRequest();
      req.registerNewAccount("10675003", "hoge", UserType.TEACHER).fire(); //$NON-NLS-1$ //$NON-NLS-2$
    }
    // get unregistered user
    {
      AccountRequest req = RequestFactoryUtil.requestFactory().accountRequest();
      req.getAccountById("aaaa").fire(new Receiver<AccountProxy>() { //$NON-NLS-1$

            @Override
            public void onSuccess(AccountProxy response) {
              assertNull(response);
            }

          });
    }
    // get registered user
    {
      AccountRequest req = RequestFactoryUtil.requestFactory().accountRequest();
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
    AccountRequest req = RequestFactoryUtil.requestFactory().accountRequest();
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

  @Test
  public void testLogin() {
    AccountRequest req = RequestFactoryUtil.requestFactory().accountRequest();
    req.login("10675003", "hoge").fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void response) {
        // TODO 自動生成されたメソッド・スタブ

      }

    });
  }

}
