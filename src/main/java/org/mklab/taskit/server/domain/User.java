package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class User extends AbstractEntity<String> {

  private String id;
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
   * @param id アカウントID
   * @param name 名前
   * @param type ユーザー種別
   */
  public User(String id, String name, UserType type) {
    if (id == null) throw new NullPointerException();
    if (name == null) throw new NullPointerException();
    if (type == null) throw new NullPointerException();
    this.id = id;
    this.name = name;
    this.type = type;
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

  // service methods

  /**
   * アカウントIDからユーザーオブジェクトを取得します。
   * 
   * @param accountId アカウントID
   * @return ユーザーオブジェクト
   */
  @Invoker({UserType.TA, UserType.STUDENT, UserType.TEACHER})
  public static User getUserByAccountId(String accountId) {
    User user = ServiceUtil.findEntity(User.class, accountId);
    return user;
  }

  /**
   * ログインユーザー情報を取得します。
   * 
   * @return ログインユーザー情報。ログインしていなければnull
   */
  public static User getLoginUser() {
    User loginUser = ServiceUtil.getLoginUser();
    return loginUser;
  }

}
