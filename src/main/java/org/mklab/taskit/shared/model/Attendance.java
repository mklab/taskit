/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * 出欠に関するクラスです。
 * 
 * @author teshima
 * @version $Revision$, Jan 28, 2011
 */
@Entity(name = "ATTENDANCE")
public class Attendance extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = -2297779723580546537L;
  @GeneratedValue
  @Id
  private int attendanceId = 0;
  /** 出席状態を表します。 */
  private int attendanceTypeId;
  /** 遅刻を表します。 */
  private boolean late;
  /** 早退を表します。 */
  private boolean earlyLeft;
  /** 講義のIDです。 */
  private int lectureId;
  /** 学籍番号です。 */
  private int accountId;
  



  /**
   * {@link Attendance}オブジェクトを構築します。
   * 
   * @param attendanceType 出席のタイプ
   * @param late 遅刻したかどうか
   * @param earlyLeft 早退したかどうか
   * @param lectureId 講義ID
   * @param accountId アカウントID
   */
  public Attendance(int attendanceType, boolean late, boolean earlyLeft, int lectureId, int accountId) {
    super();
    this.attendanceTypeId = attendanceType;
    this.late = late;
    this.earlyLeft = earlyLeft;
    this.lectureId = lectureId;
    this.accountId = accountId;
  }

  /**
   * attendanceIdを取得します。
   * 
   * @return attendanceId
   */
  public int getAttendanceId() {
    return this.attendanceId;
  }

  /**
   * attendanceIdを設定します。
   * 
   * @param attendanceId attendanceId
   */
  public void setAttendanceId(int attendanceId) {
    this.attendanceId = attendanceId;
  }

  /**
   * attendanceを設定します。
   * 
   * @param attendance attendance
   */
  public void setAttendanceTypeId(int attendance) {
    this.attendanceTypeId = attendance;
  }

  /**
   * attendanceを取得します。 　*
   * 
   * @return attendance
   */
  public int getAttendanceTypeId() {
    return this.attendanceTypeId;
  }

  /**
   * lateを設定します。
   * 
   * @param late late
   */
  public void setLate(boolean late) {
    this.late = late;
  }

  /**
   * lateを取得します。
   * 
   * @return late
   */
  public boolean isLate() {
    return this.late;
  }

  /**
   * earlyLeftを設定します。
   * 
   * @param earlyLeft earlyLeft
   */
  public void setEarlyLeft(boolean earlyLeft) {
    this.earlyLeft = earlyLeft;
  }

  /**
   * earlyLeftを取得します。
   * 
   * @return earlyLeft
   */
  public boolean isEarlyLeft() {
    return this.earlyLeft;
  }

  /**
   * lectureIdを設定します。
   * 
   * @param lectureId lectureId
   */
  public void setLectureId(int lectureId) {
    this.lectureId = lectureId;
  }

  /**
   * lectureIdを取得します。 　*
   * 
   * @return lessonId
   */
  public int getLectureId() {
    return this.lectureId;
  }

  /**
   * studentNoを設定します。
   * 
   * @param studentNo studentNo
   */
  public void setAccountId(int studentNo) {
    this.accountId = studentNo;
  }

  /**
   * studentNoを取得します。 　*
   * 
   * @return studentNo
   */
  public int getAccountId() {
    return this.accountId;
  }
}
