/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;

import org.mklab.taskit.shared.model.Lecture;


/**
 * 出席ビューで講義の選択が変更されたときに転送されるデータオブジェクトです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class AttendanceDto implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -3716375602172699191L;
  private Lecture lecture;
  private int[] attendances;

  /**
   * {@link AttendanceDto}オブジェクトを構築します。
   */
  AttendanceDto() {
    // empty
  }

  /**
   * {@link AttendanceDto}オブジェクトを構築します。
   * 
   * @param lecture 講義データ
   * @param attendances 出欠データ
   */
  public AttendanceDto(Lecture lecture, int[] attendances) {
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
  public int[] getAttendances() {
    return this.attendances;
  }

  /**
   * lectureを設定します。
   * 
   * @param lecture lecture
   */
  void setLecture(Lecture lecture) {
    this.lecture = lecture;
  }

  /**
   * attendancesを設定します。
   * 
   * @param attendances attendances
   */
  void setAttendances(int[] attendances) {
    this.attendances = attendances;
  }
}
