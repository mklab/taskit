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

  UserType getType() {
    return this.type;
  }

  void setType(UserType type) {
    this.type = type;
  }

  String getName() {
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
