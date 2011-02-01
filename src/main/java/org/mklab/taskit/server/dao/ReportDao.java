/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Report;


/**
 * @author teshima
 * @version $Revision$, Jan 25, 2011
 */
public interface ReportDao {

  /**
   * 課題IDを利用して課題内容を取得します。
   * 
   * @param id 課題ID
   * @return 課題内容を返します。
   */
  Report getReportFromID(int id);

  /**
   * 課題の日付を利用して課題内容を取得します。
   * 
   * @param date 課題の出された日付
   * @return 課題内容を返します。
   */
  List<Report> getReportFromDate(String date);
  /**
   * 課題の新規登録を行ないます。
   * 
   * @param report 登録する課題
   * @throws ReportRegistrationException レポートの登録に失敗した場合
   */
  void registerReport(Report report) throws ReportRegistrationException;

}
