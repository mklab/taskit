/**
 * 
 */
package org.mklab.taskit.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.mklab.taskit.dao.AccountDao;
import org.mklab.taskit.dao.AccountDaoImpl;
import org.mklab.taskit.model.Account;
import org.mklab.taskit.model.User;
import org.mklab.taskit.service.LoginFailureException;
import org.mklab.taskit.service.LoginFailureException.ErrorCode;
import org.mklab.taskit.service.LoginService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginServiceImpl extends TaskitRemoteService implements LoginService {

  /** */
  private static final long serialVersionUID = 4825121917291115924L;
  private AccountDao accountDao;

  /**
   * {@link LoginServiceImpl}オブジェクトを構築します。
   */
  public LoginServiceImpl() {
    this.accountDao = new AccountDaoImpl(createEntityManager());
  }

  /**
   * @see org.mklab.taskit.service.LoginService#login(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public User login(String id, String password) throws LoginFailureException {
    if (id.length() == 0) throw new LoginFailureException(ErrorCode.INVALID_ID);
    if (password.length() == 0) throw new LoginFailureException(ErrorCode.INVALID_PASSWORD);

    final Account account = this.accountDao.getHashedPasswordIfExists(id);
    if (account == null) throw new LoginFailureException(ErrorCode.ID_NOT_EXISTS);
    final String hashedPassword = account.getPassword();

    boolean valid = false;
    try {
      valid = BCrypt.checkpw(password, hashedPassword);
    } catch (IllegalArgumentException ex) {
      valid = false;
    }
    if (valid == false) throw new LoginFailureException(ErrorCode.WRONG_PASSWORD);

    final HttpServletRequest request = getThreadLocalRequest();
    final HttpSession session = request.getSession(true);

    session.setAttribute(SessionUtil.IS_LOGGED_IN_KEY, Boolean.TRUE);
    final String sessionId = session.getId();
    return new User(id, account.getAccountType(), sessionId);
  }

  /**
   * @see org.mklab.taskit.service.LoginService#logout()
   */
  @Override
  public void logout() {
    final HttpServletRequest request = getThreadLocalRequest();
    final HttpSession session = request.getSession(false);
    if (session == null) return;

    session.invalidate();
  }

}
