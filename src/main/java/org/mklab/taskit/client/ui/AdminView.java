/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ui.admin.LectureEditor;
import org.mklab.taskit.client.ui.admin.NewAccountView;
import org.mklab.taskit.client.ui.admin.ReportEditor2;


/**
 * 管理者用ビューを提供するクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public interface AdminView extends TaskitView {

  /**
   * 新規アカウント作成ビューを取得します。
   * 
   * @return 新規アカウント作成ビュー
   */
  NewAccountView getNewAccountView();

  /**
   * 講義データを編集するエディタを取得します。
   * 
   * @return 講義データを編集するエディタ
   */
  LectureEditor getLectureEditor();

  /**
   * 課題データを編集するエディタを取得します。
   * 
   * @return 課題データを編集するエディタ
   */
  ReportEditor2 getReportEditor();
}
