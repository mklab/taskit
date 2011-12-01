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
