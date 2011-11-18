/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.CheckMapProxy;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;


/**
 * {@link CheckInListView}の実装クラスです。
 * 
 * @author Yuhi Ishikura
 */
public class CheckInListViewImpl extends AbstractTaskitView implements CheckInListView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, CheckInListViewImpl> {
    // empty
  }

  @UiField(provided = true)
  CellTable<CheckMapProxy> table;
  @UiField
  Button reloadButton;

  private Presenter presenter;

  /**
   * {@link CheckInListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CheckInListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCheckInList(List<CheckMapProxy> checks) {
    this.table.setRowData(checks);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @UiHandler("reloadButton")
  void updateButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.reloadButtonPressed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Messages messages = getClientFactory().getMessages();
    this.table = new CellTable<CheckMapProxy>();
    this.table.addColumn(new Column<CheckMapProxy, String>(new TextCell()) {

      @Override
      public String getValue(CheckMapProxy object) {
        return object.getId();
      }
    }, messages.userIdLabel());
    this.table.addColumn(new Column<CheckMapProxy, String>(new TextCell()) {

      @Override
      public String getValue(CheckMapProxy object) {
        return object.getStudent().getId();
      }
    }, messages.studentLabel());
    this.table.addColumn(new Column<CheckMapProxy, String>(new TextCell()) {

      private final DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

      @Override
      public String getValue(CheckMapProxy object) {
        final Date date = object.getDate();
        return this.format.format(date);
      }
    }, messages.checkInTimeLabel());

    final Widget widget = binder.createAndBindUi(this);
    this.reloadButton.setText(messages.reloadLabel());
    return widget;
  }

}
