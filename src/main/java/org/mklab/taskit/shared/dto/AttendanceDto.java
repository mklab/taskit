/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;
import java.util.Map;

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
  private Map<String, Integer> attendances;

  /**
   * {@link AttendanceDto2}オブジェクトを構築します。
   */
  AttendanceDto() {
    // empty
  }

  /**
   * {@link AttendanceDto2}オブジェクトを構築します。
   * 
   * @param lecture 講義データ
   * @param attendances 出欠データ
   */
  public AttendanceDto(Lecture lecture, Map<String, Integer> attendances) {
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
  Map<String, Integer> getAttendances() {
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
   * 与えられたユーザーの出席状況を取得します。
   * 
   * @param userName ユーザー名
   * @return 出席状況。データがない場合には-1を返します。
   */
  public int getAttendanceTypeIndex(String userName) {
    Integer v = this.attendances.get(userName);
    if (v == null) return -1;
    return v.intValue();
  }

  /**
   * attendancesを設定します。
   * 
   * @param attendances attendances
   */
  void setAttendances(Map<String, Integer> attendances) {
    this.attendances = attendances;
  }
}
