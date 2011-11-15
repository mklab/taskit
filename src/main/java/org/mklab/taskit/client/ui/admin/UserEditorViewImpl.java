/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 */
public class UserEditorViewImpl extends AbstractTaskitView implements UserEditView {

  static interface Binder extends UiBinder<Widget, UserEditorViewImpl> {
    // empty. for UI bindings.
  }

  @UiField(provided = true)
  CellList<UserProxy> userList;

  /**
   * {@link UserEditorViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public UserEditorViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.userList = new CellList<UserProxy>(new AbstractCell<UserProxy>() {

      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, UserProxy value, SafeHtmlBuilder sb) {
        sb.appendEscaped(value.getAccount().getId());
      }
    });
    final Binder binder = GWT.create(Binder.class);
    return binder.createAndBindUi(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserList(List<UserProxy> users) {
    this.userList.setRowData(users);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEdittingUser(UserProxy user) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    // TODO Auto-generated method stub

  }
}
