/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * 課題を編集するビューです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public interface ReportEditor extends IsWidget {

  /**
   * 課題を所属させる講義のインデックスを取得します。
   * 
   * @return 課題を所属させる講義のインデックス
   */
  int getLectureIndex();

  /**
   * 課題番号を取得します。
   * 
   * @return 課題番号
   */
  int getReportNumber();

  /**
   * 講義データを設定します。
   * 
   * @param lecture 講義データ
   */
  void setLectures(Lecture[] lecture);

  /**
   * 講義データに含まれる課題データを設定します。
   * 
   * @param l 講義データ
   * @param reports 課題データ
   */
  void setReports(Lecture l, Report[] reports);

  /**
   * 課題のタイトルを取得します。
   * 
   * @return 課題のタイトル
   */
  String getReportTitle();

  /**
   * 課題の詳細を取得します。
   * 
   * @return 課題の詳細
   */
  String getReportDetail();

  /**
   * Submitボタンを取得します。
   * 
   * @return Submitボタン
   */
  HasClickHandlers getSubmitTrigger();

}
