/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.user.client.ui.IsWidget;


/**
 * ページ上部のヘッダを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public interface HeaderView extends IsWidget {

  /**
   * ユーザーIDを設定します。
   * 
   * @param id ユーザーID
   */
  void setUserId(String id);

  /**
   * ユーザー種別を設定します。
   * 
   * @param type ユーザー種別
   */
  void setUserType(String type);

  /**
   * ボタンを追加します。
   * 
   * @param name ボタン名
   * @return 生成したボタン
   */
  ToolBarButton addButton(String name);

  /**
   * ボタンの間にセパレータを追加します。
   */
  void addSeparator();

  /**
   * 高さをpxで取得します。
   * 
   * @return 高さ
   */
  int getHeight();

}
