/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreModel.LectureScore;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * @author ishikura
 */
public class StudentListViewImpl extends AbstractTaskitView implements StudentListView {

  @UiField(provided = true)
  CellList<UserProxy> list;
  @UiField(provided = true)
  StudentScorePanel panel;
  @UiField
  Label userName;
  private Presenter presenter;
  private static final Binder binder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, StudentListViewImpl> {
    // empty
  }

  /**
   * {@link StudentListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListViewImpl(ClientFactory clientFactory) {
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
  public void setSelectedListData(UserProxy user) {
    this.list.getSelectionModel().setSelected(user, true);
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
  public void highlightRow(LectureScore rowData) {
    this.panel.highlightRow(rowData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.panel = new StudentScorePanel(true);

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
        StudentListViewImpl.this.presenter.listSelectionChanged(selected);
      }
    });

    return binder.createAndBindUi(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showUserPage(UserProxy user, StudentScoreModel model) {
    this.userName.setText(user.getName() != null ? user.getName() : "<< Not set >>"); //$NON-NLS-1$
    this.panel.showUserPage(model);
  }

  @UiHandler("uncallButton")
  void uncallButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.uncall(getSelectedUser().getAccount());
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
