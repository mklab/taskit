/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import org.mklab.taskit.client.ui.HeaderView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSpacer;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 4, 2011
 */
public class SmartHeaderView extends Composite implements HeaderView {

  /**
   * {@link SmartHeaderView}オブジェクトを構築します。
   */
  public SmartHeaderView() {
    final ToolStrip toolStrip = new ToolStrip();
    
    final Menu menu = new Menu();
    final ToolStripButton button = new ToolStripButton();
    button.setIcon(GWT.getModuleBaseURL() + "check.png");
    button.setSize("64px", "64px");
    button.setIconSize(50);
    
    final ToolStripButton button2 = new ToolStripButton();
    button2.setIcon(GWT.getModuleBaseURL()+"wc.png");
    button2.setSize("64px", "64px");
    button2.setIconSize(50);
    
    final MenuItem item = new MenuItem("Student List");
    item.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(MenuItemClickEvent event) {
        SC.say("Hello");
      }
    });
    menu.addItem(item);
    
    toolStrip.setHeight(60);
    toolStrip.addButton(button);
    toolStrip.addButton(button2);
    toolStrip.setWidth100();
    initWidget(toolStrip);
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

  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#setUserType(java.lang.String)
   */
  @Override
  public void setUserType(String type) {

  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getStudentListLink()
   */
  @Override
  public HasClickHandlers getStudentListLink() {
    return new Button();
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getAttendenceListLink()
   */
  @Override
  public HasClickHandlers getAttendenceListLink() {
    return new Button();
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getAdminLink()
   */
  @Override
  public HasClickHandlers getAdminLink() {
    return new Button();
  }

  /**
   * @see org.mklab.taskit.client.ui.HeaderView#getLogoutTrigger()
   */
  @Override
  public HasClickHandlers getLogoutTrigger() {
    return new Button();
  }

}
