/**
 * 
 */
package org.mklab.taskit.server;

import org.mindrot.jbcrypt.BCrypt;


/**
 * パスワードに関するユーティリティクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class Passwords {

  /**
   * パスワードが正しいかどうかチェックします。
   * 
   * @param rawPassword パスワードそのもの
   * @param hashedDbPassword DB上に登録されているハッシュ関数にかけられたパスワード
   * @return 正しいかどうか
   */
  public static boolean checkPassword(String rawPassword, String hashedDbPassword) {
    try {
      return BCrypt.checkpw(rawPassword, hashedDbPassword);
    } catch (Throwable e) {
      return false;
    }
  }

  /**
   * パスワードをハッシュ関数にかけます。
   * 
   * @param rawPassword 生のパスワード
   * @return ハッシュ関数にかけたパスワード
   */
  public static String hashPassword(String rawPassword) {
    return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
  }

}
