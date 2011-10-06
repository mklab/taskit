/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class ProfileViewImpl extends AbstractTaskitView implements ProfileView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, ProfileViewImpl> {
    // empty
  }

  @UiField
  TextBox userName;
  @UiField
  PasswordTextBox currentPassword;
  @UiField
  PasswordTextBox password1;
  @UiField
  PasswordTextBox password2;

  private Presenter presenter;

  /**
   * {@link ProfileViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public ProfileViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  @UiHandler("userNameChangeButton")
  void handleUserNameChange(@SuppressWarnings("unused") ClickEvent evt) {
    final String newUserName = this.userName.getText();
    try {
      Validator.validateUserName(newUserName);
    } catch (IllegalArgumentException e) {
      showInformationMessage(e.getMessage());
      return;
    }

    this.presenter.changeUserName(newUserName);
  }

  @UiHandler("passwordChangeButton")
  void handlePasswordChange(@SuppressWarnings("unused") ClickEvent evt) {
    @SuppressWarnings("hiding")
    final String currentPassword = this.currentPassword.getText();
    final String newPassword = this.password1.getText();
    final String newPasswordForConfirmation = this.password2.getText();

    if (newPassword.equals(newPasswordForConfirmation) == false) {
      showInformationMessage("Password pair was wrong."); //$NON-NLS-1$
      return;
    }
    try {
      Validator.validatePassword(newPassword);
    } catch (IllegalArgumentException e) {
      showInformationMessage(e.getMessage());
      return;
    }

    this.presenter.changePassword(currentPassword, newPassword);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUser(UserProxy user) {
    this.userName.setText(user.getName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Widget widget = binder.createAndBindUi(this);
    return widget;
  }

}
