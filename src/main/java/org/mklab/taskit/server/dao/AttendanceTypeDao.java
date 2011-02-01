/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.AttendanceType;


/**
 * @author teshima
 * @version $Revision$, Feb 1, 2011
 */
public interface AttendanceTypeDao {

  /**
   * 出席種別を登録します。
   * 
   * @param attendanceType 出席種別
   */
  public void registerAttendanceType(AttendanceType attendanceType);
  /**
   * 出席種別のリストを返します。
   * @return 出席種別のリスト
   */
  public List<String> getAllAttendanceTypes();
}
