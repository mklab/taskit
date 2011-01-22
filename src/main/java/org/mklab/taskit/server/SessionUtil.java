/**
 * 
 */
package org.mklab.taskit.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mklab.taskit.model.User;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public final class SessionUtil {

  static final String IS_LOGGED_IN_KEY = "loggedIn"; //$NON-NLS-1$

  /**
   * {@link SessionUtil}オブジェクトを構築します。
   */
  private SessionUtil() {
    // do nothing
  }

  /**
   * ユーザーがログインしているかどうか調べます。
   * 
   * @param request リクエスト
   * @param user ユーザー
   * @return ログインしていればtrue,そうでなければfalse
   */
  public static boolean isLoggedIn(HttpServletRequest request, User user) {
    final HttpSession session = request.getSession(false);
    if (session == null) return false;

    final Boolean isLoggedIn = (Boolean)session.getAttribute(IS_LOGGED_IN_KEY);
    final String validSessionId = session.getId();
    if (validSessionId.equals(user.getSessionId()) == false) return false;

    if (isLoggedIn == null) return false;
    return isLoggedIn.booleanValue();
  }

}
