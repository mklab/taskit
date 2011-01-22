/**
 * 
 */
package org.mklab.taskit.client.event;

import com.google.gwt.event.shared.GwtEvent;


/**
 * ログインイベントを表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginEvent extends GwtEvent<LoginHandler> {

  private static final GwtEvent.Type<LoginHandler> TYPE = new GwtEvent.Type<LoginHandler>();

  /**
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public GwtEvent.Type<LoginHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
   */
  @Override
  protected void dispatch(LoginHandler handler) {
    handler.onLogin(this);
  }

}
