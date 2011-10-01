/**
 * 
 */
package org.mklab.taskit.shared;

/**
 * @author ishikura
 */
public class Validator {

  /**
   * パスワードを検証します。
   * 
   * @param password パスワード
   * @throws IllegalArgumentException パスワードが不正な場合
   */
  public static void validatePassword(String password) {
    validateLength(password, 6, 24);
    validateBlank(password);
  }

  /**
   * ユーザー名を検証します。
   * 
   * @param userName ユーザー名
   * @throws IllegalArgumentException ユーザー名が不正な場合
   */
  public static void validateUserName(String userName) {
    validateLength(userName, 4, 24);
  }

  private static void validateBlank(String s) {
    if (s.matches(".*?\\s.*")) throw new IllegalArgumentException("Blank characters not allowed."); //$NON-NLS-1$//$NON-NLS-2$
  }

  private static void validateLength(String s, int min, int max) {
    if (s.length() < min) throw new IllegalArgumentException("too short!"); //$NON-NLS-1$
    if (s.length() > max) throw new IllegalArgumentException("too long!"); //$NON-NLS-1$
  }

}
