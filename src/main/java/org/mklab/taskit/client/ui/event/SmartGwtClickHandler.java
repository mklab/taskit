/**
 * 
 */
package org.mklab.taskit.client.ui.event;

import com.smartgwt.client.widgets.events.ClickEvent;


/**
 * taskitのクリックハンドラーをSmartGWTのクリックハンドラーに適合させるクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class SmartGwtClickHandler implements com.smartgwt.client.widgets.events.ClickHandler {

  private ClickHandler handler;

  /**
   * {@link SmartGwtClickHandler}オブジェクトを構築します。
   * 
   * @param handler ハンドラー
   */
  public SmartGwtClickHandler(ClickHandler handler) {
    if (handler == null) throw new NullPointerException();
    this.handler = handler;
  }

  /**
   * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
   */
  @Override
  public void onClick(@SuppressWarnings("unused") ClickEvent event) {
    this.handler.onClick();
  }

}
