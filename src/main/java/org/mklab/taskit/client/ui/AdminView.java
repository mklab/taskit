/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;


/**
 * @author ishikura
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

}
