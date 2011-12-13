/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Label;
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

  @UiField
  Button passwordChangeButton;
  @UiField
  Button userNameChangeButton;
  @UiField
  Label currentPasswordLabel;
  @UiField
  Label password1Label;
  @UiField
  Label password2Label;
  @UiField
  CaptionPanel userNameCaption;
  @UiField
  CaptionPanel passwordCaption;

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
      showErrorMessage(e.getMessage());
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
      showErrorMessage(e.getMessage());
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
    final Messages messages = getClientFactory().getMessages();
    localizeMessages(messages);

    return widget;
  }

  @SuppressWarnings("nls")
  private void localizeMessages(final Messages messages) {
    this.currentPasswordLabel.setText(messages.passwordCurrentLabel() + ": ");
    this.password1Label.setText(messages.passwordNewLabel() + ": ");
    this.password2Label.setText(messages.passwordLabelForConfirmation() + ": ");
    this.passwordChangeButton.setText(messages.changeLabel());
    this.userNameChangeButton.setText(messages.changeLabel());
    this.userNameCaption.setCaptionText(messages.userNameLabel());
    this.passwordCaption.setCaptionText(messages.passwordLabel());
  }

}
