/**
 * 
 */
package org.mklab.taskit.client.ui.event;

import com.google.gwt.event.dom.client.ClickEvent;


/**
 * GWTのClickHandlerをtaskitのClickHandlerに適合させるアダプターです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class GwtClickHandler implements com.google.gwt.event.dom.client.ClickHandler {

  private ClickHandler handler;

  /**
   * {@link GwtClickHandler}オブジェクトを構築します。
   * 
   * @param handler ハンドラー
   */
  public GwtClickHandler(ClickHandler handler) {
    if (handler == null) throw new NullPointerException();
    this.handler = handler;
  }

  /**
   * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
   */
  @Override
  public void onClick(@SuppressWarnings("unused") ClickEvent event) {
    this.handler.onClick();
  }

}
