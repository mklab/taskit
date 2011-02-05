/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.event.ClickHandler;
import org.mklab.taskit.client.ui.event.SmartGwtClickHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 4, 2011
 */
public class SmartGwtHeaderView extends Composite implements HeaderView {

  private ToolStripButton studentListButton;
  private ToolStripButton checkListButton;
  private ToolStripButton attendanceButton;
  private ToolStripButton adminLinkButton;
  private ToolStripButton logoutButton;
  private Label userTypeLabel;
  private Label userNameLabel;

  /**
   * {@link SmartGwtHeaderView}オブジェクトを構築します。
   */
  public SmartGwtHeaderView() {
    final ToolStrip toolStrip = createToolBar();
    initWidget(toolStrip);
  }

  @SuppressWarnings("nls")
  private ToolStrip createToolBar() {
    final ToolStrip toolStrip = new ToolStrip();
    toolStrip.setWidth100();

    // toolbar buttons
    this.studentListButton = createToolStripButton("students64.png");
    this.checkListButton = createToolStripButton("check64.png");
    this.attendanceButton = createToolStripButton("attendance64.png");
    this.adminLinkButton = createToolStripButton("admin64.png");
    this.logoutButton = createToolStripButton("logout64.png");

    // user information display
    this.userNameLabel = new Label();
    this.userTypeLabel = new Label();
    final VerticalPanel labelsPanel = new VerticalPanel();
    labelsPanel.add(this.userNameLabel);
    labelsPanel.add(this.userTypeLabel);

    toolStrip.addButton(this.studentListButton);
    toolStrip.addButton(this.checkListButton);
    toolStrip.addButton(this.attendanceButton);
    toolStrip.addSeparator();
    toolStrip.addButton(this.adminLinkButton);
    toolStrip.addFill();
    toolStrip.addMember(labelsPanel);
    toolStrip.addButton(this.logoutButton);

    return toolStrip;
  }

  private ToolStripButton createToolStripButton(String iconName) {
    return createToolStripButton(null, iconName);
  }

  @SuppressWarnings("nls")
  private ToolStripButton createToolStripButton(String title, String iconName) {
    final ToolStripButton button = new ToolStripButton(title);
    button.setIcon(GWT.getModuleBaseURL() + iconName);
    button.setSize("40px", "40px");
    button.setIconSize(32);
    return button;
  }

  /**
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this;
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#setUserId(java.lang.String)
   */
  @Override
  public void setUserId(String id) {
    this.userNameLabel.setText(id);
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
    this.studentListButton.addClickHandler(new SmartGwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addAttendanceListLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addAttendanceListLinkClickHandler(ClickHandler h) {
    this.attendanceButton.addClickHandler(new SmartGwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addAdminLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addAdminLinkClickHandler(ClickHandler h) {
    this.adminLinkButton.addClickHandler(new SmartGwtClickHandler(h));
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#addLogoutLinkClickHandler(org.mklab.taskit.client.ui.event.ClickHandler)
   */
  @Override
  public void addLogoutLinkClickHandler(ClickHandler h) {
    this.logoutButton.addClickHandler(new SmartGwtClickHandler(h));
  }

}
