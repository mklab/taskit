/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.ToolBarButton;
import org.mklab.taskit.client.ui.event.ClickHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class CwHeaderView extends Composite implements HeaderView {

  interface Binder extends UiBinder<Widget, CwHeaderView> {
    // empty
  }

  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  HorizontalPanel buttonPanel;

  /**
   * {@link CwHeaderView}オブジェクトを構築します。
   */
  public CwHeaderView() {
    initWidget(binder.createAndBindUi(this));
    this.buttonPanel.add(new Button("A"));
    this.buttonPanel.add(new Button("B"));
    this.buttonPanel.add(new Button("C"));
    for (Widget w : this.buttonPanel) {
      this.buttonPanel.setCellVerticalAlignment(w, HasVerticalAlignment.ALIGN_MIDDLE);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeight() {
    return 32;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserId(String id) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserType(String type) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addButton(ToolBarButton button) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addStudentListLinkClickHandler(ClickHandler h) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addAttendanceListLinkClickHandler(ClickHandler h) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addAdminLinkClickHandler(ClickHandler h) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addLogoutLinkClickHandler(ClickHandler h) {
    // TODO Auto-generated method stub

  }

}
