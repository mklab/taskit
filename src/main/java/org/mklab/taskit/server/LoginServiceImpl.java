/**
 * 
 */
package org.mklab.taskit.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.mklab.taskit.dao.AccountDao;
import org.mklab.taskit.dao.AccountDaoImpl;
import org.mklab.taskit.service.LoginFailureException;
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
  public void login(String id, String password) throws LoginFailureException {
    if (id.length() == 0 || password.length() == 0) throw new LoginFailureException("Empty id or password not were not allowed.");

    final String hashedPassword = this.accountDao.getHashedPasswordIfExists(id);
    if (hashedPassword == null) throw new LoginFailureException("Your ID is not registered.");

    boolean valid = false;
    try {
      valid = BCrypt.checkpw(password, hashedPassword);
    } catch (IllegalArgumentException ex) {
      valid = false;
    }
    if (valid == false) throw new LoginFailureException("Wrong password.");

    final HttpServletRequest request = getThreadLocalRequest();
    final HttpSession session = request.getSession(true);

    session.setAttribute(SessionUtil.IS_LOGGED_IN_KEY, Boolean.TRUE);
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
