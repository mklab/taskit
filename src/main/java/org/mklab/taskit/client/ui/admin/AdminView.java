/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.event.dom.client.HasClickHandlers;


/**
 * 管理者用のビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface AdminView extends TaskitView {

  /**
   * 講義データ編集アクティビティのリンクを取得します。
   * 
   * @return 講義データ編集アクティビティのリンク
   */
  HasClickHandlers getLectureEditorLink();

  /**
   * 課題データ編集アクティビティのリンクを取得します。
   * 
   * @return 課題データ編集アクティビティのリンク
   */
  HasClickHandlers getReportEditorLink();

  /**
   * ユーザーデータ編集アクティビティへのリンクを取得します。
   * 
   * @return ユーザーデータ編集アクティビティへのリンク
   */
  HasClickHandlers getUserEditorLink();

}
