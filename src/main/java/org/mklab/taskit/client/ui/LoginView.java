/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class LoginView extends Composite implements HasClickHandlers {

  private static LoginScreenUiBinder uiBinder = GWT.create(LoginScreenUiBinder.class);

  interface LoginScreenUiBinder extends UiBinder<Widget, LoginView> {
    // no members
  }

  @UiField
  private Button loginButton;
  @UiField
  private TextBox userIdBox;
  @UiField
  private PasswordTextBox passwordBox;

  /**
   * {@link LoginView}オブジェクトを構築します。
   */
  public LoginView() {
    initWidget(uiBinder.createAndBindUi(this));
    this.loginButton.setText("Login");

    final KeyDownHandler enterHandler = new KeyDownHandler() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER == false) return;

        enterKeyPressed();
      }
    };

    this.userIdBox.addKeyDownHandler(enterHandler);
    this.passwordBox.addKeyDownHandler(enterHandler);
  }

  private void enterKeyPressed() {
    this.loginButton.click();
  }

  /**
   * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
   */
  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return this.loginButton.addClickHandler(handler);
  }

}
