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
package org.mklab.taskit.client.model;

import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.UserProxy;


/**
 * 講義別の全生徒の出席データリストの、単一生徒の出席状況を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class AttendanceListItem {

  private UserProxy user;
  private AttendanceProxy attendance;

  /**
   * {@link AttendanceListItem}オブジェクトを構築します。
   * 
   * @param user ユーザー
   * @param attendance 出席データ。まだ記録されていない場合はnull
   */
  public AttendanceListItem(UserProxy user, AttendanceProxy attendance) {
    if (user == null) throw new NullPointerException();
    this.user = user;
    this.attendance = attendance;
  }

  /**
   * userを取得します。
   * 
   * @return user
   */
  public UserProxy getUser() {
    return this.user;
  }

  /**
   * attendanceを取得します。
   * 
   * @return attendance.記録されていない場合はnull
   */
  public AttendanceProxy getAttendance() {
    return this.attendance;
  }

}
