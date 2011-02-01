/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * 出席種別を表す列挙型です。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
@Entity(name = "ATTENDANCE_TYPE")
public class AttendanceType {

  @GeneratedValue()
  @Id
  private int attendanceTypeId;
  private String type;

  /**
   * {@link AttendanceType}オブジェクトを構築します。
   * 
   * @param type タイプ
   */
  public AttendanceType(String type) {
    this.type = type;
  }
  /**
   * attendanceTypeIdを取得します。
   * 
   * @return attendanceTypeId
   */
  public int getAttendanceTypeId() {
    return this.attendanceTypeId;
  }

  /**
   * attendanceTypeIdを設定します。
   * 
   * @param attendanceTypeId attendanceTypeId
   */
  public void setAttendanceTypeId(int attendanceTypeId) {
    this.attendanceTypeId = attendanceTypeId;
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
   * typeを設定します。
   * 
   * @param type type
   */
  public void setType(String type) {
    this.type = type;
  }

}
