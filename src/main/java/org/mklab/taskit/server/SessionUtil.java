/**
 * 
 */
package org.mklab.taskit.server;

import javax.servlet.http.HttpSession;

import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.model.UserType;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
final class SessionUtil {

  static final String IS_LOGGED_IN_KEY = "loggedIn"; //$NON-NLS-1$
  static final String USER_KEY = "user"; //$NON-NLS-1$

  /**
   * {@link SessionUtil}オブジェクトを構築します。
   */
  private SessionUtil() {
    // do nothing
  }

  /**
   * ユーザーがログインしているかどうか調べます。
   * 
   * @param session セッション, nullが与えられた場合には必ずfalseを返します。
   * @return ログインしていればtrue,そうでなければfalse
   */
  static boolean isLoggedIn(final HttpSession session) {
    if (session == null) return false;
    final Boolean isLoggedIn = (Boolean)session.getAttribute(IS_LOGGED_IN_KEY);

    if (isLoggedIn == null) return false;
    return isLoggedIn.booleanValue();
  }

  /**
   * セッションに保存されたユーザーオブジェクトを取得します。
   * 
   * @param request リクエスト
   * @return ユーザー
   */
  static User getUser(final HttpSession session) {
    if (session == null) throw new IllegalStateException("session == null!!"); //$NON-NLS-1$
    if (isLoggedIn(session) == false) return null;

    final User user = (User)session.getAttribute(USER_KEY);
    return user;
  }

  /**
   * ユーザー種別をセッションから検出します。
   * 
   * @param request リクエスト
   * @return ユーザー種別
   */
  static UserType getUserType(final HttpSession session) {
    return getUser(session).getType();
  }

  /**
   * TAか先生であるかどうかチェックします。
   * 
   * @param session セッション
   * @throws IllegalStateException TAでも先生でもない場合
   */
  static void assertIsTAOrTeacher(HttpSession session) {
    if (isTAOrTeacher(session)) return;

    throw new IllegalStateException("You are not TA or teacher, or your session was timeout."); //$NON-NLS-1$
  }

  /**
   * TAもしくは先生であるかどうか調べます。
   * 
   * @param session セッション
   * @return TAか先生であるかどうか。ログインしていない場合にはfalseを返します。
   */
  static boolean isTAOrTeacher(final HttpSession session) {
    if (isLoggedIn(session) == false) return false;
    final UserType userType = getUserType(session);
    return userType == UserType.TA || userType == UserType.TEACHER;
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
