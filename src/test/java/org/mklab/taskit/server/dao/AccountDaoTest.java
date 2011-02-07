/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.List;

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

  private final static String STUDENT = "STUDENT";
  private final static String TA = "TA";
  private final static String TEACHER = "TEACHER";
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
    final String name = "10675003";
    try {
      dao.registerAccount(name, Passwords.hashPassword("taskit"), "TA");
    } catch (AccountRegistrationException e) {
      e.printStackTrace();
      fail();
    }
    dao.registerAccount(name, Passwords.hashPassword("taskit"), "TA");
  }
  
  /**
   * 生徒のユーザ名一覧を取得できるかテストします。
   * @throws AccountRegistrationException 
   */
  @Test
  public void testGetAllStudentUserName() throws AccountRegistrationException {
    final AccountDao dao = new AccountDaoImpl(createEntityManager());
    dao.registerAccount("10236001", "taskit", STUDENT);
    dao.registerAccount("10236002", "taskit", STUDENT);
    dao.registerAccount("10236003", "taskit", STUDENT);
    dao.registerAccount("10236004", "taskit", STUDENT);
    dao.registerAccount("10236005", "taskit", STUDENT);
    dao.registerAccount("10675001", "taskit", TA);
    dao.registerAccount("10675002", "taskit", TA);
    dao.registerAccount("10675003", "taskit", TA);
    dao.registerAccount("koga", "taskit", TEACHER);
    
    List<String> userNames = dao.getAllStudentUserNames();
    assertEquals("10236001", userNames.get(0));    
    assertEquals("10236002", userNames.get(1));    
    assertEquals("10236003", userNames.get(2));    
    assertEquals("10236004", userNames.get(3));
    assertEquals("10236005", userNames.get(4));
    assertEquals(5, userNames.size());
  }

}
