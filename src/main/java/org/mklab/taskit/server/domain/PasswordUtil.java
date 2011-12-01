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

import org.mklab.taskit.shared.Validator;

import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;


/**
 * パスワードに関するユーティリティクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class PasswordUtil {

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

  /**
   * パスワードを生成します。
   * 
   * @param length パスワードの長さ
   * @return パスワード
   */
  public static String generatePassword(int length) {
    final Set<Character> passwordCharacterSet = Validator.getValidPasswordCharacters();
    Character[] c = passwordCharacterSet.toArray(new Character[passwordCharacterSet.size()]);

    final StringBuilder password = new StringBuilder();
    for (int i = 0; i < length; i++) {
      password.append(c[(int)(Math.random() * c.length)]);
    }
    return password.toString();
  }

}
