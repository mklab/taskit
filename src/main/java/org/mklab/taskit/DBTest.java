/**
 * 
 */
package org.mklab.taskit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.mindrot.jbcrypt.BCrypt;
import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public class DBTest {

  public static void main(String[] args) throws AccountRegistrationException {
    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final AccountDao dao = new AccountDaoImpl(entityManager);
    dao.registerAccount("10675003", BCrypt.hashpw("taskit", BCrypt.gensalt()), "TA");
  }

}
