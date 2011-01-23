/**
 * 
 */
package org.mklab.taskit.model;

import java.io.Serializable;


/**
 * ユーザーを表すクラスです。
 * <p>
 * 全ての操作でこのオブジェクトをやり取りすることでユーザーを判別します。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class User implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -4185866051536719116L;
  private String id;
  private UserType type;

  /**
   * {@link User}オブジェクトを構築します。
   * <p>
   * for serialization.
   */
  public User() {
    // empty
  }

  /**
   * {@link User}オブジェクトを構築します。
   * 
   * @param id ID
   * @param type ユーザーの種別
   */
  public User(String id, UserType type) {
    if (id == null || type == null) throw new NullPointerException();
    this.id = id;
    this.type = type;
  }

  /**
   * ユーザーIDを取得します。
   * 
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * ユーザー種別を取得します。
   * <p>
   * この値はあくまでユーザーへどの権限があるか通知するためのもので、実際の権限に関してはサーバー内部で保持するため安全です。
   * 
   * @return type
   */
  public UserType getType() {
    return this.type;
  }

}
