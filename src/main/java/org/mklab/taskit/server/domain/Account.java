package org.mklab.taskit.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mklab.taskit.server.Passwords;
import org.mklab.taskit.server.auth.AuthenticationEntryPoint;
import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Account extends AbstractEntity<String> {

  private String id;
  private String hashedPassword;

  @Column(name = "password")
  String getHashedPassword() {
    return this.hashedPassword;
  }

  void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(String id) {
    this.id = id;
  }

  /**
   * パスワードを設定します。
   * 
   * @param password パスワード
   */
  @Invoker({UserType.TEACHER, UserType.TA, UserType.STUDENT})
  public void changePassword(String password) {
    this.hashedPassword = Passwords.hashPassword(password);
    update();
  }

  /**
   * データベースに変更を反映します。
   */
  private void update() {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    t.begin();
    try {
      em.merge(this);
      t.commit();
    } catch (Throwable ex) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * アカウントを新規登録します。
   * 
   * @param id ID
   * @param password パスワード
   * @param userType ユーザー種別
   */
  @Invoker({UserType.TEACHER})
  public static void registerNewAccount(String id, String password, UserType userType) {
    if (isAlreadyRegistered(id)) throw new IllegalArgumentException("id already exists."); //$NON-NLS-1$
    final String hashedPassword = Passwords.hashPassword(password);
    final Account account = new Account();
    account.setId(id);
    account.setHashedPassword(hashedPassword);
    final User user = new User();
    user.setId(id);
    user.setType(userType);

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    t.begin();
    try {
      em.persist(account);
      em.persist(user);
      t.commit();
    } catch (Throwable ex) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * ログインします。
   * 
   * @param id ID
   * @param password パスワード
   */
  @AuthenticationEntryPoint
  public static void login(String id, String password) {
    if (isAlreadyRegistered(id) == false) throw new IllegalArgumentException("ID not exists."); //$NON-NLS-1$
    final Account account = getAccountById(id);
    if (Passwords.checkPassword(password, account.getHashedPassword()) == false) {
      throw new IllegalArgumentException("Password invalid."); //$NON-NLS-1$
    }

    final HttpServletRequest request = RequestFactoryServlet.getThreadLocalRequest();
    final HttpSession session = request.getSession(true);

    final User user = AbstractLocator.findEntity(User.class, id);
    if (user == null) {
      throw new IllegalArgumentException("Internal error: user data not exists."); //$NON-NLS-1$
    }

    session.setAttribute("isLoggedIn", Boolean.TRUE);
    session.setAttribute("user", user);
  }

  private static boolean isAlreadyRegistered(String id) {
    return getAccountById(id) != null;
  }

  /**
   * IDからアカウントを取得します。
   * 
   * @param id ID
   * @return アカウント
   */
  @Invoker({UserType.TEACHER, UserType.TA})
  public static Account getAccountById(String id) {
    final EntityManager em = EMF.get().createEntityManager();
    final Account account = em.find(Account.class, id);
    return account;
  }

}
