/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * @author ishikura
 */
public class CwStudentListView extends AbstractTaskitView implements StudentListView {

  @UiField(provided = true)
  CellList<UserProxy> list;
  @UiField
  StudentScorePanel panel;
  private Presenter presenter;
  private static final Binder binder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, CwStudentListView> {
    // empty
  }

  /**
   * {@link CwStudentListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwStudentListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    this.panel.setPresenter(presenter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(List<UserProxy> listData) {
    this.list.setRowData(listData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearUserPage() {
    this.panel.clearScoreData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Cell<UserProxy> userCell = new AbstractCell<UserProxy>() {

      @Override
      public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, UserProxy value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div style='font-size:2em;'>"); //$NON-NLS-1$
        sb.appendHtmlConstant(value.getAccount().getId());
        sb.appendHtmlConstant("</div>"); //$NON-NLS-1$
      }

    };
    this.list = new CellList<UserProxy>(userCell);
    final SingleSelectionModel<UserProxy> selectionModel = new SingleSelectionModel<UserProxy>();
    this.list.setSelectionModel(selectionModel);
    this.list.getSelectionModel().addSelectionChangeHandler(new Handler() {

      @SuppressWarnings({"synthetic-access", "unused"})
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        final UserProxy selected = selectionModel.getSelectedObject();
        CwStudentListView.this.presenter.listSelectionChanged(selected);
      }
    });

    return binder.createAndBindUi(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showUserPage(UserProxy user, StudentScoreModel model) {
    this.panel.showUserPage(user, model);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public UserProxy getSelectedUser() {
    return ((SingleSelectionModel<UserProxy>)this.list.getSelectionModel()).getSelectedObject();
  }

}
