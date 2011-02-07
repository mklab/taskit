/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 4, 2011
 */
public class GwtHasClickListenersAdapter implements HasClickHandlers {

  com.smartgwt.client.widgets.events.HasClickHandlers handlers;

  /**
   * {@link GwtHasClickListenersAdapter}オブジェクトを構築します。
   * 
   * @param handlers ハンドラ
   */
  public GwtHasClickListenersAdapter(com.smartgwt.client.widgets.events.HasClickHandlers handlers) {
    super();
    this.handlers = handlers;
  }

  /**
   * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
   */
  @Override
  public void fireEvent(GwtEvent<?> event) {
    this.handlers.fireEvent(event);
  }

  /**
   * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
   */
  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return this.handlers.addClickHandler(new SmartGwtClickHandlerAdapter(handler));
  }

  class SmartGwtClickHandlerAdapter implements com.smartgwt.client.widgets.events.ClickHandler {

    ClickHandler h;

    /**
     * {@link SmartGwtClickHandlerAdapter}オブジェクトを構築します。
     * 
     * @param h
     */
    SmartGwtClickHandlerAdapter(ClickHandler h) {
      super();
      this.h = h;
    }

    /**
     * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
     */
    @SuppressWarnings("unused")
    @Override
    public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
      ClickEvent gwtEvent = new ClickEvent() {
        // empty
      };
      this.h.onClick(gwtEvent);
    }

  }

}
