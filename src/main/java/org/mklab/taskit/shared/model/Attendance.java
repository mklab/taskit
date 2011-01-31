/**
 * 
 */
package org.mklab.taskit.shared.model;

/**
 * 出欠に関するクラスです。
 * 
 * @author teshima
 * @version $Revision$, Jan 28, 2011
 */
public class Attendance {

  /** 出席状態を表します。 */
  private int attendance;
  /** 遅刻を表します。 */
  private boolean late;
  /** 早退を表します。 */
  private boolean earlyLeft;
  /** 欠席 */
  private final static int ABSENT = 0;
  /** 出席 */
  private final static int ATTENDANCE = 1;
  /** 病欠 */
  private final static int ILLNESS = 2;
  /** 忌引き */
  private final static int BEREAVEMENT = 3;
  /**
   * {@link Attendance}オブジェクトを構築します。
   * 
   * @param attendance 出席状況
   * @param late 遅刻
   * @param earlyLeft 早退
   */
  public Attendance(int attendance, boolean late, boolean earlyLeft) {
    super();
    this.setAttendance(attendance);
    this.setLate(late);
    this.setEarlyLeft(earlyLeft);
  }
  /**
   * attendanceを設定します。
   *
   * @param attendance attendance
   */
  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }
  /**
   * attendanceを取得します。
  　*
   * @return attendance
   */
  public int getAttendance() {
    return attendance;
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
    return late;
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
    return earlyLeft;
  }
}
