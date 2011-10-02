package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.Passwords;
import org.mklab.taskit.server.auth.AuthenticationEntryPoint;
import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.shared.Validator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Account extends AbstractEntity<String> {

  private String id;
  private String hashedPassword;

  /**
   * ハッシュ化したパスワードを取得します。
   * 
   * @return ハッシュ化したパスワード
   */
  @Column(name = "password")
  public String getHashedPassword() {
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
   * ログインユーザーのパスワードを変更します。
   * 
   * @param oldPassword 古いパスワード
   * @param newPassword 新しいパスワード
   */
  @Invoker({UserType.TEACHER, UserType.TA, UserType.STUDENT})
  public static void changeMyPassword(String oldPassword, String newPassword) {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    if (Passwords.checkPassword(oldPassword, loginUser.getAccount().getHashedPassword()) == false) {
      throw new IllegalArgumentException("Current password was wrong."); //$NON-NLS-1$
    }

    changePassword(loginUser.getAccount(), newPassword);
  }

  /**
   * 指定されたユーザーのパスワードを変更します。
   * 
   * @param account アカウント
   * @param newPassword パスワード
   */
  @Invoker({UserType.TEACHER})
  public static void changePassword(Account account, String newPassword) {
    Validator.validatePassword(newPassword);

    account.setHashedPassword(Passwords.hashPassword(newPassword));
    account.update();
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
    user.setAccount(account);
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
   * 与えられたIDのアカウントの登録を抹消します。
   * 
   * @param id ID
   */
  @Invoker({UserType.TEACHER})
  public static void unregisterAccount(String id) {
    final Account account = getAccountById(id);
    if (account == null) throw new IllegalArgumentException("Not registered: " + id); //$NON-NLS-1$

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    t.begin();
    try {
      em.remove(account);
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
   * @return ログインユーザー情報
   */
  @AuthenticationEntryPoint
  public static User login(String id, String password) {
    if (isAlreadyRegistered(id) == false) throw new IllegalArgumentException("ID not exists."); //$NON-NLS-1$
    final Account account = getAccountById(id);
    if (Passwords.checkPassword(password, account.getHashedPassword()) == false) {
      throw new IllegalArgumentException("Password invalid."); //$NON-NLS-1$
    }

    final User user = User.getUserByAccountId(id);
    if (user == null) {
      throw new IllegalArgumentException("Internal error: user data not exists."); //$NON-NLS-1$
    }

    ServiceUtil.login(user);
    return user;
  }

  /**
   * ログアウトします。
   */
  @Invoker({UserType.STUDENT, UserType.TA, UserType.TEACHER})
  public static void logout() {
    if (ServiceUtil.isLoggedIn() == false) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$
    ServiceUtil.logout();
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
