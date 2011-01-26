/**
 * 
 */
package org.mklab.taskit.shared.model;

/**
 * ユーザー種別を表す列挙型です。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public enum UserType {

  /**
   * 講師を表す定数です。
   */
  TEACHER,
  /**
   * TAを表す定数です。
   */
  TA,
  /**
   * 学生を表す定数です。
   */
  STUDENT;

  /**
   * 文字列からユーザー種別を取得します。
   * 
   * @param type ユーザー種別を表す文字列
   * @return ユーザー種別
   */
  public static UserType fromString(String type) {
    if (type == null) throw new NullPointerException();

    final String upperType = type.toUpperCase();
    try {
      return UserType.valueOf(upperType);
    } catch (Throwable e) {
      return null;
    }
  }

}
