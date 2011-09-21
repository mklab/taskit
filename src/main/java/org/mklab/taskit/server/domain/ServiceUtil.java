/**
 * 
 */
package org.mklab.taskit.server.domain;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;


/**
 * @author yuhi
 */
class ServiceUtil {

  static final String IS_LOGGED_IN_KEY = "loggedIn"; //$NON-NLS-1$
  static final String USER_KEY = "user"; //$NON-NLS-1$

  /**
   * 与えられたエンティティのクラスのテーブルを参照し、IDが一致するエンティティを取得します。
   * 
   * @param clazz エンティティのクラス
   * @param id エンティティのID
   * @return エンティティ
   */
  static <T, I> T findEntity(Class<? extends T> clazz, I id) {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      return em.find(clazz, id);
    } finally {
      em.close();
    }
  }

  /**
   * 与えられたユーザーがログイン状態であることをセッションに対しマークします。
   * 
   * @param user ログインさせるユーザー
   */
  static void markAsLoggedIn(User user) {
    final HttpSession session = getSession();
    session.setAttribute(IS_LOGGED_IN_KEY, Boolean.TRUE);
    session.setAttribute(USER_KEY, user);
  }

  /**
   * ログインユーザーを取得します。
   * 
   * @return ログインユーザー。ログインしていない場合はnull
   */
  static User getLoginUser() {
    final HttpSession session = getSession();
    if (session == null) return null;

    final User user = (User)session.getAttribute(USER_KEY);
    return user;
  }

  /**
   * ログインしているかどうか調べます。
   * 
   * @return ログインしているかどうか
   */
  static boolean isLoggedIn() {
    final HttpSession session = getSession();
    if (session == null) return false;

    final Boolean loggedIn = (Boolean)session.getAttribute(IS_LOGGED_IN_KEY);
    return loggedIn == null ? false : loggedIn.booleanValue();
  }

  /**
   * セッションを取得します。
   * <p>
   * まだセッションが生成されていない場合は新規に作成します。
   * 
   * @return セッション
   */
  static final HttpSession getSessionOrCreate() {
    return RequestFactoryServlet.getThreadLocalRequest().getSession(true);
  }

  /**
   * セッションを取得します。
   * <p>
   * セッションが存在しない場合はnullを返します。
   * 
   * @return セッション
   */
  static final HttpSession getSession() {
    return RequestFactoryServlet.getThreadLocalRequest().getSession();
  }
}
