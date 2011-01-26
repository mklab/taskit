/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public class NewAccountViewImpl extends Composite implements NewAccountView {

  private static NewAccountViewUiBinder uiBinder = GWT.create(NewAccountViewUiBinder.class);

  interface NewAccountViewUiBinder extends UiBinder<Widget, NewAccountViewImpl> {
    // no members
  }

  @UiField
  InlineLabel idLabel;
  @UiField
  TextBox idText;
  @UiField
  InlineLabel passwordLabel;
  @UiField
  PasswordTextBox passwordText;
  @UiField
  InlineLabel passwordLabelForConfirmation;
  @UiField
  PasswordTextBox passwordTextForConfirmation;
  @UiField
  InlineLabel accountTypeLabel;
  @UiField
  ListBox accountTypeList;
  @UiField
  Button submitButton;

  /**
   * {@link NewAccountViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public NewAccountViewImpl(ClientFactory clientFactory) {
    initWidget(uiBinder.createAndBindUi(this));

    final Messages messages = clientFactory.getMessages();

    this.idLabel.setText(messages.idLabel());
    this.passwordLabel.setText(messages.passwordLabel());
    this.passwordLabelForConfirmation.setText(messages.passwordLabelForConfirmation());

    this.accountTypeLabel.setText(messages.accountTypeLabel());
    this.accountTypeList.addItem(messages.taLabel());
    this.accountTypeList.addItem(messages.studentLabel());
    this.submitButton.setText(messages.registerButton());
  }

  /**
   * @see org.mklab.taskit.client.ui.NewAccountView#getUserId()
   */
  @Override
  public String getUserId() {
    return this.idText.getText();
  }

  /**
   * @see org.mklab.taskit.client.ui.NewAccountView#getPassword()
   */
  @Override
  public String getPassword() {
    return this.passwordText.getText();
  }

  /**
   * @see org.mklab.taskit.client.ui.NewAccountView#getPasswordForComfimation()
   */
  @Override
  public String getPasswordForComfimation() {
    return this.passwordTextForConfirmation.getText();
  }

  /**
   * @see org.mklab.taskit.client.ui.NewAccountView#getAccountType()
   */
  @Override
  public String getAccountType() {
    final int selectedIndex = this.accountTypeList.getSelectedIndex();
    if (selectedIndex == -1) return null;

    return this.accountTypeList.getValue(selectedIndex);
  }

  /**
   * @see org.mklab.taskit.client.ui.NewAccountView#getSubmitTrigger()
   */
  @Override
  public HasClickHandlers getSubmitTrigger() {
    return this.submitButton;
  }

}
