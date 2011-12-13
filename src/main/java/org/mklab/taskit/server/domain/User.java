/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.shared.Validator;

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
   * @param type ユーザー種別
   */
  public User(Account account, UserType type) {
    if (account == null) throw new NullPointerException();
    if (type == null) throw new NullPointerException();
    this.account = account;
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
  public void setId(Integer id) {
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

  /**
   * ユーザー名を設定します。
   * 
   * @param name ユーザー名
   */
  public void setName(String name) {
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
  public static void changeMyUserName(String userName) {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    Validator.validateUserName(userName);

    loginUser.setName(userName);
    loginUser.update();
  }

  /**
   * ユーザー名を変更します。
   * 
   * @param accountId アカウントID
   * @param userName ユーザー名
   */
  @Invoker(UserType.TEACHER)
  public static void changeUserName(String accountId, String userName) {
    User user = getUserByAccountId(accountId);
    if (user == null) throw new IllegalStateException("unknown account id:" + accountId); //$NON-NLS-1$

    Validator.validateUserName(userName);

    user.setName(userName);
    user.update();
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
    @SuppressWarnings("unchecked")
    final List<User> res = q.getResultList();
    if (res.size() == 0) throw new IllegalArgumentException("No such user : " + accountId); //$NON-NLS-1$
    return res.get(0);
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
   * 全ユーザーを取得します。
   * 
   * @return 全ユーザーのリスト
   */
  @Invoker(UserType.TEACHER)
  public static List<User> getAllUsers() {
    return ServiceUtil.getAllEntities("User"); //$NON-NLS-1$
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
