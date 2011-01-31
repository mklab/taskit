/**
 * 
 */
package org.mklab.taskit.shared.service;

import org.mklab.taskit.shared.dto.AttendanceDto;
import org.mklab.taskit.shared.dto.AttendanceBaseDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * 出席管理サービスを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
@RemoteServiceRelativePath("attendance")
public interface AttendanceService extends RemoteService {

  /**
   * ユーザーの指定された講義の出席種別を設定します。
   * 
   * @param userName ユーザー名
   * @param lectureIndex 講義
   * @param attendanceType 出席種別
   */
  void setAttendanceType(String userName, int lectureIndex, String attendanceType);

  /**
   * 指定された講義の、学生の出席状況を全て取得します。
   * 
   * @param lectureIndex 講義
   * @return 出席状況
   */
  AttendanceDto getLecturewiseAttendanceData(int lectureIndex);

  /**
   * 出席ビューのベースデータ（講義数、ユーザー名集合）を取得します。
   * 
   * @return 出席ビューのベースデータ
   */
  AttendanceBaseDto getBaseData();

}