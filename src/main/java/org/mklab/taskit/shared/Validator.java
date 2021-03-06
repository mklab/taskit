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
package org.mklab.taskit.shared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author ishikura
 */
public class Validator {

  private static Set<Character> passwordCharacters;

  static {
    initPassCharacters();
  }

  @SuppressWarnings("boxing")
  private static void initPassCharacters() {
    if (passwordCharacters != null) return;

    final Set<Character> set = new HashSet<Character>();
    for (char c = 'A'; c <= 'Z'; c++) {
      set.add(Character.valueOf(c));
    }
    for (char c = 'a'; c <= 'z'; c++) {
      set.add(Character.valueOf(c));
    }
    for (char c = '0'; c <= '9'; c++) {
      set.add(Character.valueOf(c));
    }
    set.addAll(Arrays.asList('.', '/', '%', '&', '@', '#', '!', '^', '*', '-', '+', '~', '?', '_'));
    passwordCharacters = set;
  }

  /**
   * パスワードを検証します。
   * 
   * @param password パスワード
   * @throws IllegalArgumentException パスワードが不正な場合
   */
  @SuppressWarnings("nls")
  public static void validatePassword(String password) {
    validateLength(password, 6, 24);
    validateBlank(password);

    for (final char c : password.toCharArray()) {
      if (passwordCharacters.contains(Character.valueOf(c)) == false) {
        throw new IllegalArgumentException("'" + c + "'" + " is not a valid character as a password.");
      }
    }
  }

  /**
   * ユーザー名を検証します。
   * 
   * @param userName ユーザー名
   * @throws IllegalArgumentException ユーザー名が不正な場合
   */
  public static void validateUserName(String userName) {
    validateLength(userName, 0, 24);
  }

  /**
   * ユーザーIDを検証します。
   * 
   * @param userId ユーザーID
   */
  public static void validateUserId(String userId) {
    validateLength(userId, 4, 24);
  }

  private static void validateBlank(String s) {
    if (s.matches(".*?\\s.*")) throw new IllegalArgumentException("Blank characters not allowed."); //$NON-NLS-1$//$NON-NLS-2$
  }

  private static void validateLength(String s, int min, int max) {
    if (s.length() < min) throw new IllegalArgumentException("too short!"); //$NON-NLS-1$
    if (s.length() > max) throw new IllegalArgumentException("too long!"); //$NON-NLS-1$
  }

  /**
   * パスワードとして有効な文字の配列を取得します。
   * 
   * @return パスワードとして有効な文字の配列
   */
  public static Set<Character> getValidPasswordCharacters() {
    initPassCharacters();
    return passwordCharacters;
  }

}
