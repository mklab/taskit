/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.shared.HelpCallListItemProxy;
import org.mklab.taskit.shared.HelpCallProxy;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * @author ishikura
 */
public class HelpCallListViewImpl extends AbstractTaskitView implements HelpCallListView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, HelpCallListViewImpl> {
    // empty
  }

  private Presenter presenter;
  @UiField(provided = true)
  CellList<HelpCallListItemProxy> list;
  @UiField
  Label messageLabel;
  @UiField
  Button checkInListButton;

  /**
   * {@link HelpCallListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public HelpCallListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  @UiHandler("checkInListButton")
  void checkInListButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.goToCheckInList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHelpCalls(List<HelpCallListItemProxy> helpCalls) {
    if (helpCalls.isEmpty()) {
      this.messageLabel.setText(getClientFactory().getMessages().nooneCallingMessage());
    } else {
      this.messageLabel.setText(getClientFactory().getMessages().callingMessage(String.valueOf(helpCalls.size())));
    }

    this.list.setRowData(helpCalls);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.list = new CellList<HelpCallListItemProxy>(new AbstractCell<HelpCallListItemProxy>() {

      @SuppressWarnings({"deprecation", "nls"})
      @Override
      public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, HelpCallListItemProxy value, SafeHtmlBuilder sb) {
        final HelpCallProxy helpCall = value.getHelpCall();
        if (helpCall == null) throw new IllegalArgumentException();
        final Date date = helpCall.getDate();
        final String callerId = helpCall.getCaller().getId();
        final String message = helpCall.getMessage();
        final boolean isReceived = value.getUsersInCharge().size() > 0;

        sb.appendHtmlConstant("<div style='background-color:" + (isReceived ? "#ddd" : "white") + ";'>");
        sb.appendHtmlConstant(date.toLocaleString());
        sb.appendHtmlConstant("<br>");
        appendCallerInfo(sb, callerId, message);
        if (isReceived) {
          sb.appendHtmlConstant("<br>");
          appendUsersInCharge(sb, value);
        }
        sb.appendHtmlConstant("</div>");
      }

      @SuppressWarnings("nls")
      private void appendUsersInCharge(SafeHtmlBuilder sb, HelpCallListItemProxy value) {
        if (value.getUsersInCharge().size() == 0) return;

        final List<String> usersInCharge = value.getUsersInCharge();
        final StringBuilder userList = new StringBuilder();
        for (int i = 0; i < usersInCharge.size(); i++) {
          if (i != 0) userList.append(",");
          userList.append(usersInCharge.get(i));
        }

        sb.appendHtmlConstant("<i>");
        sb.appendEscaped("==>");
        sb.appendEscaped(getClientFactory().getMessages().receivedByMessage(userList.toString()));
        sb.appendHtmlConstant("</i>");
      }

      @SuppressWarnings("nls")
      private void appendCallerInfo(SafeHtmlBuilder sb, final String callerId, final String message) {
        sb.appendHtmlConstant("<b>" + SafeHtmlUtils.htmlEscape(callerId) + "</b>");
        if (message != null && message.length() > 0) {
          sb.appendHtmlConstant("<font color='red'> '");
          sb.appendEscaped(message);
          sb.appendHtmlConstant("'</font>");
        }
      }

    });

    final SingleSelectionModel<HelpCallListItemProxy> selectionModel = new SingleSelectionModel<HelpCallListItemProxy>();
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSelectionChange(@SuppressWarnings("unused") SelectionChangeEvent event) {
        final HelpCallListItemProxy selectedCall = selectionModel.getSelectedObject();
        HelpCallListViewImpl.this.presenter.helpCallSelected(selectedCall.getHelpCall());
      }
    });
    this.list.setSelectionModel(selectionModel);

    final Widget widget = binder.createAndBindUi(this);
    this.checkInListButton.setText(getClientFactory().getMessages().checkInListLabel());

    return widget;
  }

}
