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
public class AccountDaoImpl implements AccountDao {

  private EntityManager entityManager;

  /**
   * {@link AccountDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public AccountDaoImpl(EntityManager entityManager) {
    if (entityManager == null) throw new NullPointerException();
    this.entityManager = entityManager;
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#getHashedPasswordIfExists(java.lang.String)
   */
  @Override
  public Account getHashedPasswordIfExists(String userName) {
    final Query q = this.entityManager.createQuery(String.format("select a from ACCOUNT a where userName='%s'", userName)); //$NON-NLS-1$
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

    final Account account = new Account(userName, hashedPassword, type);

    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();

    try {
      this.entityManager.persist(account);
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
    return getHashedPasswordIfExists(id) != null;
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#getAllStudentUserNames()
   */
  @Override
  public List<String> getAllStudentUserNames() {
    Query query = this.entityManager.createQuery("SELECT userName FROM ACCOUNT WHERE accountType = 'STUDENT'"); //$NON-NLS-1$
    @SuppressWarnings("unchecked")
    List<String> studentIDs = query.getResultList();
    return studentIDs;
  }
}
