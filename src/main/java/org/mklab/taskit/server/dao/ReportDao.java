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
public interface ReportDao extends Dao {

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
   * 与えられたIDの講義のレポートを取得します。
   * 
   * @param lectureId 講義ID
   * @return 講義のレポート
   */
  List<Report> getReportsFromLectureId(int lectureId);

  /**
   * 課題の新規登録を行ないます。
   * 
   * @param report 登録する課題
   * @throws ReportRegistrationException レポートの登録に失敗した場合
   */
  void registerReport(Report report) throws ReportRegistrationException;

  /**
   * 全ての課題をリストで返します。
   * 
   * @return 全ての課題のリスト
   */
  List<Report> getAllReports();

  /**
   * 第何回目の何番目の課題のタイトルを取得します。
   * 
   * @param lectureId 講義IDです。
   * @param no 何番目かを表します。
   * @return 課題のタイトル
   */
  String getTitle(int lectureId, int no);

  /**
   * 第何回目の何番目の課題の詳細を取得します。
   * 
   * @param lectureId 講義ID
   * @param no 何番目かを表します。
   * @return 課題の詳細
   */
  String getDetail(int lectureId, int no);
}
