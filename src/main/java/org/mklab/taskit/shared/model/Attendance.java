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
  private int typeId;
  /** 遅刻を表します。 */
  private boolean late;
  /** 早退を表します。 */
  private boolean earlyLeft;
  /** 講義のIDです。 */
  private int lessonId;
  /** 学籍番号です。 */
  private int accountId;

  /**
   * {@link Attendance}オブジェクトを構築します。
   * 
   * @param attendance 出席状況
   * @param late 遅刻
   * @param earlyLeft 早退
   */
  public Attendance(int attendance, boolean late, boolean earlyLeft) {
    super();
    this.setTypeId(attendance);
    this.setLate(late);
    this.setEarlyLeft(earlyLeft);
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
  public void setTypeId(int attendance) {
    this.typeId = attendance;
  }

  /**
   * attendanceを取得します。 　*
   * 
   * @return attendance
   */
  public int getTypeId() {
    return this.typeId;
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
   * lessonIdを設定します。
   * 
   * @param lessonId lessonId
   */
  public void setLessonId(int lessonId) {
    this.lessonId = lessonId;
  }

  /**
   * lessonIdを取得します。 　*
   * 
   * @return lessonId
   */
  public int getLessonId() {
    return this.lessonId;
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
