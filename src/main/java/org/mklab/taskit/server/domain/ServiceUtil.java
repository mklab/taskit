/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.service.registry.EventRegistry;
import de.novanic.eventservice.service.registry.EventRegistryFactory;


/**
 * サービスで共通に利用する処理を定義したユーティリティクラスです。
 * <p>
 * テストのために、実装を切り替えられるようにしています。
 * 
 * @author ishikura
 */
public class ServiceUtil {

  static final String IS_LOGGED_IN_KEY = "loggedIn"; //$NON-NLS-1$
  /** セッション中のユーザーオブジェクトのキーです。 */
  public static final String USER_KEY = "user"; //$NON-NLS-1$
  private static ServiceUtilImplementation impl = new DefaultServiceUtilImplementation();

  static ServiceUtilImplementation getImplementation() {
    return impl;
  }

  static void setImplementation(ServiceUtilImplementation implementation) {
    ServiceUtil.impl = implementation;
  }

  /**
   * 与えられたエンティティのクラスのテーブルを参照し、IDが一致するエンティティを取得します。
   * 
   * @param clazz エンティティのクラス
   * @param id エンティティのID
   * @return エンティティ
   */
  public static <T, I> T findEntity(Class<? extends T> clazz, I id) {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      return em.find(clazz, id);
    } finally {
      em.close();
    }
  }

  /**
   * 与えられたテーブル名のエンティティをすべて取得します。
   * 
   * @param tableName テーブル名
   * @return すべてのエンティティ
   */
  @SuppressWarnings({"nls", "unchecked"})
  public static <T> List<T> getAllEntities(String tableName) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select o from " + tableName + " o");
    try {
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  /**
   * 与えられたユーザーでログインします。
   * <p>
   * すでにログインしていた場合は一旦ログアウトしてから改めてログインします。
   * 
   * @param user ログインさせるユーザー
   */
  public static void login(User user) {
    if (isLoggedIn()) {
      logout();
    }
    impl.login(user);
  }

  /**
   * ログアウトします。
   * <p>
   * ログインしていない状態であれば何も行いません。
   */
  public static void logout() {
    impl.logout();
  }

  /**
   * ログインユーザーを取得します。
   * 
   * @return ログインユーザー。ログインしていない場合はnull
   */
  public static User getLoginUser() {
    return impl.getLoginUser();
  }

  /**
   * ログインしているかどうか調べます。
   * 
   * @return ログインしているかどうか
   */
  public static boolean isLoggedIn() {
    return impl.isLoggedIn();
  }

  /**
   * domainに興味のあるクライアントに対しイベントを配信します。
   * 
   * @param domain ドメイン
   * @param event イベント
   */
  public static void fireEvent(Domain domain, Event event) {
    final EventRegistry registory = EventRegistryFactory.getInstance().getEventRegistry();

    registory.addEvent(domain, event);
  }

  static interface ServiceUtilImplementation {

    boolean isLoggedIn();

    User getLoginUser();

    void login(User user);

    void logout();

  }

  static class DefaultServiceUtilImplementation implements ServiceUtilImplementation {

    @Override
    public void login(User user) {
      final HttpSession session = getSession();
      session.setAttribute(IS_LOGGED_IN_KEY, Boolean.TRUE);
      session.setAttribute(USER_KEY, user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {
      HttpSession session = getSession();
      if (session != null) {
        session.invalidate();
      }
    }

    @Override
    public User getLoginUser() {
      final HttpSession session = getSession();
      if (session == null) return null;

      final User user = (User)session.getAttribute(USER_KEY);
      return user;
    }

    @Override
    public boolean isLoggedIn() {
      final HttpSession session = getSession();
      if (session == null) return false;

      final Boolean loggedIn = (Boolean)session.getAttribute(IS_LOGGED_IN_KEY);
      return loggedIn == null ? false : loggedIn.booleanValue();
    }

    private static final HttpSession getSession() {
      return getThreadLocalRequest().getSession();
    }

    private static HttpServletRequest getThreadLocalRequest() {
      HttpServletRequest req = RequestFactoryServlet.getThreadLocalRequest();
      if (req == null) throw new NullPointerException("Thread local request was null."); //$NON-NLS-1$
      return req;
    }
  }
}
