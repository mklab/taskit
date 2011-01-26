/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
   * @see org.mklab.taskit.server.dao.AccountDao#getHashedPasswordIfExists(java.lang.String)
   */
  @Override
  public Account getHashedPasswordIfExists(String id) {
    final Account account = this.entityManager.find(Account.class, id);
    return account;
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#registerAccount(java.lang.String,
   *      java.lang.String, String)
   */
  @Override
  public void registerAccount(String id, String hashedPassword, String type) throws AccountRegistrationException {
    if (isRegistered(id)) throw new AccountRegistrationException("account already exists!"); //$NON-NLS-1$

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
      throw new AccountRegistrationException("account already exists!"); //$NON-NLS-1$
    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new AccountRegistrationException("failed to register an account"); //$NON-NLS-1$
      } catch (Throwable e1) {
        throw new AccountRegistrationException("failed to register an account, and rollback failed."); //$NON-NLS-1$
      }
    }
  }

  private boolean isRegistered(String id) {
    return getHashedPasswordIfExists(id) != null;
  }

  /**
   * @see org.mklab.taskit.server.dao.AccountDao#getAllStudentIDs()
   */
  @Override
  public List<String> getAllStudentIDs() {
    Query query = this.entityManager.createQuery("SELECT id FROM ACCOUNT WHERE accountType = 'STUDENT'"); //$NON-NLS-1$
    List<String> studentIDs = query.getResultList();
    return studentIDs;
  }
}
