/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.event.dom.client.ClickHandler;


/**
 * @author ishikura
 */
public interface ToolBarButton {

  /**
   * クリック処理を設定します。
   * 
   * @param h クリック処理
   */
  void setClickHandler(ClickHandler h);

  /**
   * ボタン名を設定します。
   * 
   * @param name ボタン名
   */
  void setName(String name);

  /**
   * アイコンを設定します。
   * 
   * @param icon アイコン
   */
  void setIcon(String icon);

}
