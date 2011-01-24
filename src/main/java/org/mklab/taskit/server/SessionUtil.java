/**
 * 
 */
package org.mklab.taskit.server;

import javax.servlet.http.HttpSession;

import org.mklab.taskit.shared.model.UserType;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
final class SessionUtil {

  static final String IS_LOGGED_IN_KEY = "loggedIn"; //$NON-NLS-1$
  static final String USER_TYPE_KEY = "userType"; //$NON-NLS-1$

  /**
   * {@link SessionUtil}オブジェクトを構築します。
   */
  private SessionUtil() {
    // do nothing
  }

  /**
   * ユーザーがログインしているかどうか調べます。
   * 
   * @param session セッション
   * @return ログインしていればtrue,そうでなければfalse
   */
  static boolean isLoggedIn(final HttpSession session) {
    if (session == null) return false;
    final Boolean isLoggedIn = (Boolean)session.getAttribute(IS_LOGGED_IN_KEY);

    if (isLoggedIn == null) return false;
    return isLoggedIn.booleanValue();
  }

  /**
   * ユーザー種別をセッションから検出します。
   * 
   * @param request リクエスト
   * @return ユーザー種別
   */
  static UserType getUserType(final HttpSession session) {
    if (isLoggedIn(session) == false) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    if (session == null) throw new IllegalStateException("session == null!!"); //$NON-NLS-1$

    final UserType type = (UserType)session.getAttribute(USER_TYPE_KEY);
    if (type == null) throw new IllegalStateException("User type is not stored."); //$NON-NLS-1$

    return type;
  }

  /**
   * TAであるかどうか調べます。
   * 
   * @param session セッション
   * @return TAであるかどうか。ログインしていない場合にはfalseを返します。
   */
  static boolean isTA(final HttpSession session) {
    if (isLoggedIn(session) == false) return false;
    return getUserType(session) == UserType.TA;
  }

  /**
   * 講師であるかどうか調べます。
   * 
   * @param session セッション
   * @return 講師であるかどうか。ログインしていない場合にはfalseを返します。
   */
  static boolean isTeacher(final HttpSession session) {
    if (isLoggedIn(session) == false) return false;
    return getUserType(session) == UserType.TEACHER;
  }

  /**
   * 生徒であるかどうか調べます。
   * 
   * @param session セッション
   * @return 生徒であるかどうか。ログインしていない場合にはfalseを返します。
   */
  static boolean isStudent(final HttpSession session) {
    if (isLoggedIn(session) == false) return false;
    return getUserType(session) == UserType.STUDENT;
  }
}
