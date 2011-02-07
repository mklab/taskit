/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.event.ClickHandler;
import org.mklab.taskit.client.ui.event.GwtClickHandler;

import com.google.gwt.core.client.GWT;
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
  Button studentListLink;
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

    this.userIdLabel.setText("not set"); //$NON-NLS-1$
    this.userTypeLabel.setText("not set"); //$NON-NLS-1$
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
   * @see org.mklab.taskit.client.ui.HeaderView#addStudentListLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addStudentListLinkClickHandler(ClickHandler h) {
    this.studentListLink.addClickHandler(new GwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addAttendanceListLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addAttendanceListLinkClickHandler(ClickHandler h) {
    this.attendanceLink.addClickHandler(new GwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addAdminLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addAdminLinkClickHandler(ClickHandler h) {
    this.adminLink.addClickHandler(new GwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addLogoutLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addLogoutLinkClickHandler(ClickHandler h) {
    this.logoutButton.addClickHandler(new GwtClickHandler(h));
  }

}
