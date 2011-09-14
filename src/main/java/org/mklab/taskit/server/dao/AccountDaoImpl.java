/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Account;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class AccountDaoImpl extends AbstractDao implements AccountDao {

  /**
   * {@link AccountDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AccountDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#getAccountIfExists(java.lang.String)
   */
  @Override
  public Account getAccountIfExists(String userName) {
    final EntityManager entityManager = entityManager();
    final Query q = entityManager.createQuery(String.format("select a from ACCOUNT a where userName='%s'", userName)); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    final List<Account> accountList = q.getResultList();
    if (accountList.size() == 0) return null;

    if (accountList.size() > 1) throw new IllegalStateException("Multiple user was detected!"); //$NON-NLS-1$

    return accountList.get(0);
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#registerAccount(java.lang.String,
   *      java.lang.String, String)
   */
  @Override
  public void registerAccount(String userName, String hashedPassword, String type) throws AccountRegistrationException {
    if (isRegistered(userName)) throw new AccountRegistrationException(AccountRegistrationException.ErrorCode.USER_NAME_ALREADY_EXISTS);
    final EntityManager entityManager = entityManager();
    final Account account = new Account(userName, hashedPassword, type);

    final EntityTransaction t = entityManager.getTransaction();
    t.begin();

    try {
      entityManager.persist(account);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new AccountRegistrationException(AccountRegistrationException.ErrorCode.USER_NAME_ALREADY_EXISTS);
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new AccountRegistrationException(AccountRegistrationException.ErrorCode.UNEXPECTED);
      } catch (Throwable e1) {
        throw new AccountRegistrationException(AccountRegistrationException.ErrorCode.UNEXPECTED);
      }
    }
  }

  private boolean isRegistered(String id) {
    return getAccountIfExists(id) != null;
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#getAllStudentUserNames()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<String> getAllStudentUserNames() {
    final EntityManager entityManager = entityManager();
    Query query = entityManager.createQuery("SELECT a.userName FROM ACCOUNT a WHERE a.accountType = 'STUDENT'"); //$NON-NLS-1$
    return query.getResultList();
  }
}
