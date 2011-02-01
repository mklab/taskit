/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.mklab.taskit.server.Passwords;
import org.mklab.taskit.shared.model.Account;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public abstract class DaoTest {

  private static EntityManagerFactory factory;
  private static int DUMMY_USER_INDEX = 0;

  static {
    factory = Persistence.createEntityManagerFactory("taskit-test"); //$NON-NLS-1$
  }

  /**
   * テスト前に呼び出されます。
   */
  @Before
  public void setUp() {
    factory = Persistence.createEntityManagerFactory("taskit-test"); //$NON-NLS-1$
  }

  /**
   * テスト後に呼び出されます。
   */
  @After
  public void tearDown() {
    factory.close();
  }

  protected EntityManager createEntityManager() {
    return factory.createEntityManager();
  }

  protected Account createUniqueUser(String type) {
    final AccountDao dao = new AccountDaoImpl(createEntityManager());
    final String userName = "userForTest" + DUMMY_USER_INDEX++; //$NON-NLS-1$

    try {
      dao.registerAccount(userName, Passwords.hashPassword("taskit"), type); //$NON-NLS-1$
    } catch (AccountRegistrationException e) {
      throw new RuntimeException(e);
    }
    return dao.getAccountIfExists(userName);
  }

}
