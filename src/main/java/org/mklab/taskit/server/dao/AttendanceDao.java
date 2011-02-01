/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Attendance;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public interface AttendanceDao {

  /**
   * 指定のアカウントIDの学生の出席状況を返します。
   * 
   * @param accountId アカウントID
   * @return 出席状況のリスト
   */
  public List<Attendance> getAttendanceStateFromAccountId(int accountId);

  /**
   * 日付からその日の生徒全員の出席状況を返します。
   * 
   * @param lessonId 日付
   * @return 指定した日付の生徒全員の状況
   */
  public List<Attendance> getAttendanceStateFromLessonId(int lessonId);

  /**
   * 与えられた講義の全生徒の出席状況を取得します。 取得された出席のタイプはアカウントIDの昇順で配列に格納されます。
   * 
   * @param lectureId 講義ID
   * @return 与えられた講義の全生徒の出席状況
   */
  public List<String> getAttendanceTypes(int lectureId);

  /**
   * 指定された講義の出席状況を設定します。
   * 
   * @param lectureId 講義ID
   * @param userName アカウントID　
   * @param attendanceType 出席のタイプ
   */
  public void setAttendanceType(int lectureId, String userName, String attendanceType);

  /**
   * 指定された講義の出席状況を設定します。
   * 
   */
  public void registerAttendance(Attendance attendance);
}
