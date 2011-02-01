/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * 講義データのビューです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public interface LectureEditor extends IsWidget {

  /**
   * 講義タイトルの表示データを設定します。
   * 
   * @param title 講義タイトル
   */
  void setLectureTitle(String title);

  /**
   * 表示されている講義タイトルを取得します。
   * 
   * @return 表示されている講義タイトル
   */
  String getLectureTitle();

  /**
   * 講義開始時間を設定します。
   * 
   * @param timeInMillis 講義開始時間
   */
  void setLectureStartTime(long timeInMillis);

  /**
   * 講義開始時間を取得します。
   * 
   * @return 講義開始時間
   */
  long getLectureStartTime();

  /**
   * Submitボタンを取得します。
   * 
   * @return Submitボタン
   */
  HasClickHandlers getSubmitTrigger();

}
