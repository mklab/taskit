/**
 * 
 */
package org.mklab.taskit.shared.service;

import org.mklab.taskit.shared.dto.AttendanceDto;
import org.mklab.taskit.shared.model.Lecture;


/**
 * 出席管理サービスを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public interface AttendanceService {

  /**
   * ユーザーの指定された講義の出席種別を設定します。
   * 
   * @param userName ユーザー名
   * @param lecture 講義
   * @param attendanceType 出席種別
   */
  void setAttendanceType(String userName, Lecture lecture, String attendanceType);

  /**
   * 指定された講義の、学生の出席状況を全て取得します。
   * 
   * @param lecture 講義
   * @return 出席状況
   */
  AttendanceDto[] getAttendanceTypesOfStudents(Lecture lecture);

}
