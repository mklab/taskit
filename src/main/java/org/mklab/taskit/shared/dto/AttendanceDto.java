/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.util.List;

import org.mklab.taskit.shared.model.Attendance;
import org.mklab.taskit.shared.model.Lecture;


/**
 * 出席ビューで講義の選択が変更されたときに転送されるデータオブジェクトです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDto {

  private Lecture lecture;
  private List<Attendance> attendances;

  /**
   * {@link AttendanceDto}オブジェクトを構築します。
   * 
   * @param lecture 講義データ
   * @param attendances 出欠データ
   */
  public AttendanceDto(Lecture lecture, List<Attendance> attendances) {
    this.lecture = lecture;
    this.attendances = attendances;
  }

  /**
   * lectureを取得します。
   * 
   * @return lecture
   */
  public Lecture getLecture() {
    return this.lecture;
  }

  /**
   * attendancesを取得します。
   * 
   * @return attendances
   */
  public List<Attendance> getAttendances() {
    return this.attendances;
  }
}
