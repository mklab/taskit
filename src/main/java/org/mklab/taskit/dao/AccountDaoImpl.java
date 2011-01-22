/**
 * 
 */
package org.mklab.taskit.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.model.Account;


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
   * @see org.mklab.taskit.dao.AccountDao#getHashedPasswordIfExists(java.lang.String)
   */
  @Override
  public String getHashedPasswordIfExists(String id) {
    final Query q = this.entityManager.createQuery(String.format("select password from ACCOUNT where id='%s'", id)); //$NON-NLS-1$
    final List<?> o = q.getResultList();
    if (o.size() == 0) return null;

    if (o.size() > 1) throw new IllegalStateException();

    final Object hashedPassword = o.get(0);
    if (hashedPassword instanceof String == false) throw new IllegalStateException();

    return (String)hashedPassword;
  }

  /**
   * @see org.mklab.taskit.dao.AccountDao#registerAccount(java.lang.String,
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
