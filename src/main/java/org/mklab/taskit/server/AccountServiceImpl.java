/**
 * 
 */
package org.mklab.taskit.server;

import java.util.List;

import org.mklab.taskit.server.dao.AccountDao;
import org.mklab.taskit.server.dao.AccountDaoImpl;
import org.mklab.taskit.server.dao.AccountRegistrationException;
import org.mklab.taskit.shared.model.UserType;
import org.mklab.taskit.shared.service.AccountService;
import org.mklab.taskit.shared.validation.AccountValidator;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class AccountServiceImpl extends TaskitRemoteService implements AccountService {

  /** for serialization. */
  private static final long serialVersionUID = -5693377245247949174L;
  private AccountDao accountDao;

  /**
   * {@link AccountServiceImpl}オブジェクトを構築します。
   */
  public AccountServiceImpl() {
    this.accountDao = new AccountDaoImpl(createEntityManager());
  }

  /**
   * @see org.mklab.taskit.shared.service.AccountService#createNewAccount(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  @Override
  public void createNewAccount(String userId, String password, String accountType) {
    final boolean accountIsValidPair = AccountValidator.validate(userId, password) == AccountValidator.Result.VALID;
    if (accountIsValidPair == false) throw new IllegalArgumentException("Invalid pair of id and password."); //$NON-NLS-1$
    if (UserType.fromString(accountType) == null) throw new IllegalArgumentException("Invalid account type : " + accountType); //$NON-NLS-1$

    try {
      this.accountDao.registerAccount(userId, Passwords.hashPassword(password), accountType);
    } catch (AccountRegistrationException e) {
      throw new IllegalStateException("Failed to register. : "+e.getMessage()); //$NON-NLS-1$
    }
  }

  /**
   * @see org.mklab.taskit.shared.service.AccountService#getAllStudentIDs()
   */
  @Override
  public String[] getAllStudentIDs() {
    final List<String> ids = this.accountDao.getAllStudentIDs();
    return ids.toArray(new String[ids.size()]);
  }

}
