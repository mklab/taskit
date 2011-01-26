/**
 * 
 */
package org.mklab.taskit.server.dao;

import org.mklab.taskit.shared.model.Report;


/**
 * @author teshima
 * @version $Revision$, Jan 25, 2011
 */
interface ReportDao {

  /**
   * 課題IDを利用して課題内容を取得します。
   * 
   * @param id 課題ID
   * @return 課題内容を返します。
   */
  Report getReportFromID(String id);

  /**
   * 課題の日付を利用して課題内容を取得します。
   * 
   * @param date 課題の出された日付
   * @return 課題内容を返します。
   */
  Report getReportFromDate(String date);

  /**
   * 課題の日付とその課題レベル（番号）を利用して課題内容を取得します。
   * 
   * @param date 課題の出された日付
   * @param level 課題レベル（番号）
   * @return 課題内容を返します。
   */
  Report getReportFromDateAndLevel(String date, String level);

  /**
   * 課題の新規登録を行ないます。
   * 
   * @param id 課題ID
   * @param name 課題の名前
   * @param detail TODO
   * @param date 課題の日付
   * @param level 課題のレベル（番号）
   * @param allotment 課題の配点
   * @throws ReportRegistrationException 課題がすでに登録されている場合
   */
  void registReport(String id, String name, String detail, String date, String level, String allotment) throws ReportRegistrationException;

}
