/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mindrot.jbcrypt.BCrypt;
import org.mklab.taskit.server.AccountServiceImpl;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.service.AccountRegistrationException;
import org.mklab.taskit.shared.service.AccountService;


/**
 * @author teshima
 * @version $Revision$, Jan 26, 2011
 */
public class DataAccessMain {

  public static void main(String[] args) throws ReportRegistrationException, AccountRegistrationException {
    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("taskit");
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    final AccountDao dao = new AccountDaoImpl(entityManager);
    dao.registerAccount("10675003", BCrypt.hashpw("taskit", BCrypt.gensalt()), "TA");
  }
}
