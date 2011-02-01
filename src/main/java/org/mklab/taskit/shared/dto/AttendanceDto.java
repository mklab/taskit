/**
 * 
 */
package org.mklab.taskit.shared.dto;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDto {

  private String userName;
  private int attendanceType;

  /**
   * {@link AttendanceDto}オブジェクトを構築します。
   * 
   * @param userName ユーザー名
   * @param attendanceType 出席種別
   */
  public AttendanceDto(String userName, int attendanceType) {
    super();
    this.userName = userName;
    this.attendanceType = attendanceType;
  }

  /**
   * userNameを取得します。
   * 
   * @return userName
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * attendanceTypeを取得します。
   * 
   * @return attendanceType
   */
  public int getAttendanceType() {
    return this.attendanceType;
  }

}
