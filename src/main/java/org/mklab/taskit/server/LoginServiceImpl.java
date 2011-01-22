/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.service.LoginFailureException;
import org.mklab.taskit.service.LoginService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginServiceImpl extends TaskitRemoteService implements LoginService {

  /** */
  private static final long serialVersionUID = 4825121917291115924L;

  /**
   * @see org.mklab.taskit.service.LoginService#login(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public void login(String id, String password) throws LoginFailureException {
    if (id.length() > 0 && password.length() > 0) return;

    throw new LoginFailureException("no accounts match");
  }

  /**
   * @see org.mklab.taskit.service.LoginService#logout()
   */
  @Override
  public void logout() {
    throw new UnsupportedOperationException();
  }

}
