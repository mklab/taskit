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
  private String type;
  private String sessionId;

  /**
   * {@link User}オブジェクトを構築します。
   * 
   * @param id ID
   * @param type ユーザーの種別
   * @param sessionId セッションID
   */
  public User(String id, String type, String sessionId) {
    if (id == null || type == null || sessionId == null) throw new NullPointerException();
    this.id = id;
    this.type = type;
    this.sessionId = sessionId;
  }

  /**
   * idを取得します。
   * 
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * typeを取得します。
   * 
   * @return type
   */
  public String getType() {
    return this.type;
  }

  /**
   * sessionIdを取得します。
   * 
   * @return sessionId
   */
  public String getSessionId() {
    return this.sessionId;
  }

}
