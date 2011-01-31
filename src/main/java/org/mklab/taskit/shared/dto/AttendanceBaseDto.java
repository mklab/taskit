/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;
import java.util.List;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, 2011/02/01
 */
public class AttendanceBaseDto implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 8411404526275386015L;
  private int lectureCount;
  private List<String> userNames;
  private List<String> attendanceTypes;

  /**
   * {@link AttendanceBaseDto}オブジェクトを構築します。
   */
  AttendanceBaseDto() {
    // do nothing
  }

  /**
   * {@link AttendanceBaseDto}オブジェクトを構築します。
   * 
   * @param lectureCount 全講義数
   * @param userNames 全ユーザー名
   * @param attendanceTypes 出席種別名
   */
  public AttendanceBaseDto(int lectureCount, List<String> userNames, List<String> attendanceTypes) {
    this.attendanceTypes = attendanceTypes;
    this.lectureCount = lectureCount;
    this.userNames = userNames;
  }

  /**
   * attendanceTypesを取得します。
   * 
   * @return attendanceTypes
   */
  public List<String> getAttendanceTypes() {
    return this.attendanceTypes;
  }

  /**
   * userNamesを取得します。
   * 
   * @return userNames
   */
  public List<String> getUserNames() {
    return this.userNames;
  }

  /**
   * lectureCountを取得します。
   * 
   * @return lectureCount
   */
  public int getLectureCount() {
    return this.lectureCount;
  }

}
