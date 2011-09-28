/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.smartgwt.client.widgets.events.ClickHandler;


/**
 * @author ishikura
 */
public interface ToolBarButton {

  /**
   * クリック処理を行うハンドラを設定します。
   * 
   * @param clickHandler クリック処理を行うハンドラ
   */
  void setClickHandler(ClickHandler clickHandler);

  /**
   * ボタンアイコンを取得します。
   * 
   * @return ボタンアイコン
   */
  String getIcon();

}
