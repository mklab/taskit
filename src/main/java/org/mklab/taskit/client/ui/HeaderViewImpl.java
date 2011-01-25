/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public class HeaderViewImpl extends Composite implements HeaderView {

  private static HeaderViewUiBinder uiBinder = GWT.create(HeaderViewUiBinder.class);

  interface HeaderViewUiBinder extends UiBinder<Widget, HeaderViewImpl> {
    // no members
  }

  @UiField
  Button scoreLink;
  @UiField
  Button attendanceLink;
  @UiField
  Button adminLink;
  @UiField
  InlineLabel userIdLabel;
  @UiField
  InlineLabel userTypeLabel;
  @UiField
  Button logoutButton;

  /**
   * {@link LoginViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public HeaderViewImpl(ClientFactory clientFactory) {
    initWidget(uiBinder.createAndBindUi(this));

    this.userIdLabel.setText("ID"); //$NON-NLS-1$
    this.userTypeLabel.setText("Type"); //$NON-NLS-1$
    this.logoutButton.setText(clientFactory.getMessages().logoutButton());
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#setUserId(java.lang.String)
   */
  @Override
  public void setUserId(String id) {
    this.userIdLabel.setText(id);
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#setUserType(java.lang.String)
   */
  @Override
  public void setUserType(String type) {
    this.userTypeLabel.setText(type);
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getLogoutTrigger()
   */
  @Override
  public HasClickHandlers getLogoutTrigger() {
    return this.logoutButton;
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getAdminLink()
   */
  @Override
  public HasClickHandlers getAdminLink() {
    return this.adminLink;
  }

}
