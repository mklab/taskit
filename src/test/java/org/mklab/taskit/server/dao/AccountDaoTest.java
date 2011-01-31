/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.mklab.taskit.server.Passwords;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
@SuppressWarnings("nls")
public class AccountDaoTest extends DaoTest {

  /**
   * アカウント登録のテストを行ないます。
   */
  @Test
  public void testRegisterAccount() {
    final EntityManager mgr = createEntityManager();
    final AccountDao dao = new AccountDaoImpl(mgr);
    try {
      dao.registerAccount("10675003", Passwords.hashPassword("taskit"), "TA");
    } catch (AccountRegistrationException e) {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * 同じIDで登録した際に例外がスローされるかテストします。
   * 
   * @throws AccountRegistrationException 予期した例外
   */
  @Test(expected = AccountRegistrationException.class)
  public void testRegisterAccountWithDuplicateId() throws AccountRegistrationException {
    final EntityManager mgr = createEntityManager();
    final AccountDao dao = new AccountDaoImpl(mgr);
    final String name = "asfidjoasdf23i412";
    try {
      dao.registerAccount(name, Passwords.hashPassword("taskit"), "TA");
    } catch (AccountRegistrationException e) {
      e.printStackTrace();
      fail();
    }
    dao.registerAccount(name, Passwords.hashPassword("taskit"), "TA");
  }

}
