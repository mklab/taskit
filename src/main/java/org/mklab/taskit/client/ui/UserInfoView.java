/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * ユーザー情報を表示するビューです。
 * 
 * @author Yuhi Ishikura
 */
public class UserInfoView extends Composite {

  static interface Binder extends UiBinder<Widget, UserInfoView> {
    // only for UI Binding
  }

  @UiField
  Label userIdLabel;
  @UiField
  Label userId;
  @UiField
  Label userNameLabel;
  @UiField
  Label userName;
  private Messages messages;

  /**
   * {@link UserInfoView}オブジェクトを構築します。
   * 
   * @param messages ローカライズに利用するメッセージバンドル
   */
  public UserInfoView(Messages messages) {
    this.messages = messages;
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));

    localizeMessages(messages);
  }

  @SuppressWarnings("nls")
  private void localizeMessages(Messages message) {
    this.userIdLabel.setText(message.userIdLabel() + ": ");
    this.userNameLabel.setText(message.userNameLabel() + ": ");
  }

  /**
   * 表示するユーザー情報を設定します。
   * 
   * @param user 表示するユーザー情報
   */
  @SuppressWarnings("nls")
  public void setUser(UserProxy user) {
    if (user == null) {
      clearUser();
      return;
    }

    this.userId.setText(user.getAccount().getId());
    if (user.getName() == null) {
      final String notSetLabel = "<< " + this.messages.unsetLabel() + " >>";
      this.userName.setText(notSetLabel);
    } else {
      this.userName.setText(user.getName());
    }
  }

  private void clearUser() {
    this.userId.setText(this.messages.notSelectedLabel());
    this.userName.setText(this.messages.notSelectedLabel());
  }

}
