/**
 * 
 */
package org.mklab.taskit.shared.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.mklab.taskit.shared.model.Account;


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
   * @see org.mklab.taskit.shared.dao.AccountDao#getHashedPasswordIfExists(java.lang.String)
   */
  @Override
  public Account getHashedPasswordIfExists(String id) {
    final Account account = this.entityManager.find(Account.class, id);
    return account;
  }

  /**
   * @see org.mklab.taskit.shared.dao.AccountDao#registerAccount(java.lang.String,
   *      java.lang.String, String)
   */
  @Override
  public void registerAccount(String id, String hashedPassword, String type) throws AccountRegistrationException {
    if (isRegistered(id)) throw new AccountRegistrationException("account already exists!");

    final Account account = new Account(id, hashedPassword, type);

    final EntityTransaction t = this.entityManager.getTransaction();
    t.begin();

    try {
      this.entityManager.persist(account);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new AccountRegistrationException("account already exists!");
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new AccountRegistrationException("failed to register an account");
      } catch (Throwable e1) {
        throw new AccountRegistrationException("failed to register an account, and rollback failed.");
      }
    }
  }

  private boolean isRegistered(String id) {
    return getHashedPasswordIfExists(id) != null;
  }
}
