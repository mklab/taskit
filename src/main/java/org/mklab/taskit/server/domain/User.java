package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.Validator;
import org.mklab.taskit.shared.model.UserType;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class User extends AbstractEntity<Integer> {

  private Integer id;
  private Account account;
  private UserType type;
  private String name;

  /**
   * {@link User}オブジェクトを構築します。
   */
  public User() {
    // do nothing
  }

  /**
   * {@link User}オブジェクトを構築します。
   * 
   * @param account アカウント
   * @param name 名前
   * @param type ユーザー種別
   */
  public User(Account account, String name, UserType type) {
    if (account == null) throw new NullPointerException();
    if (name == null) throw new NullPointerException();
    if (type == null) throw new NullPointerException();
    this.account = account;
    this.name = name;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(Integer id) {
    this.id = id;
  }

  /**
   * ユーザー種別を取得します。
   * 
   * @return ユーザー種別
   */
  public UserType getType() {
    return this.type;
  }

  void setType(UserType type) {
    this.type = type;
  }

  /**
   * ユーザー名を取得します。
   * 
   * @return ユーザー名
   */
  public String getName() {
    return this.name;
  }

  void setName(String name) {
    this.name = name;
  }

  /**
   * アカウントを取得します。
   * 
   * @return アカウント
   */
  @OneToOne
  public Account getAccount() {
    return this.account;
  }

  /**
   * アカウントを設定します。
   * 
   * @param account アカウント
   */
  public void setAccount(Account account) {
    this.account = account;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return MessageFormat.format("User [id={0}, name={1}, type={2}, account={3}]", this.id, this.name, this.type, this.account); //$NON-NLS-1$
  }

  // service methods

  /**
   * ユーザー名を変更します。
   * 
   * @param userName ユーザー名
   */
  @Invoker({UserType.TA, UserType.TEACHER, UserType.STUDENT})
  public static void changeUserName(String userName) {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    Validator.validateUserName(userName);

    loginUser.setName(userName);
    loginUser.update();
  }

  /**
   * アカウントIDからユーザーオブジェクトを取得します。
   * 
   * @param accountId アカウントID
   * @return ユーザーオブジェクト
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static User getUserByAccountId(String accountId) {
    EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery("select s from User s where s.account.id=:accountId"); //$NON-NLS-1$
    q.setParameter("accountId", accountId); //$NON-NLS-1$
    return (User)q.getSingleResult();
  }

  /**
   * ログインユーザー情報を取得します。
   * 
   * @return ログインユーザー情報。ログインしていなければnull
   */
  @Invoker({UserType.TA, UserType.STUDENT, UserType.TEACHER})
  public static User getLoginUser() {
    User loginUser = ServiceUtil.getLoginUser();
    return loginUser;
  }

  /**
   * 全生徒情報を取得します。
   * 
   * @return　全生徒情報
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static List<User> getAllStudents() {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select u from User u where u.type=:type order by u.account.id"); //$NON-NLS-1$
    q.setParameter("type", UserType.STUDENT); //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    final List<User> studentList = q.getResultList();
    return studentList;
  }

}
