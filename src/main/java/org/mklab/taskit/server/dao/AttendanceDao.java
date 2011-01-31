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
}
