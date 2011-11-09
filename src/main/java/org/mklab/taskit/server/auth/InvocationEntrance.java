/**
 * 
 */
package org.mklab.taskit.server.auth;

import org.mklab.taskit.server.domain.User;


/**
 * リクエストを許可するかどうか判定するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface InvocationEntrance {

  /**
   * 与えられたユーザーを受け入れるかどうか判定します。
   * 
   * @param user ユーザー
   * @return 受け入れるかどうか
   */
  boolean accept(User user);

}
