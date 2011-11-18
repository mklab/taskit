/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.shared.HelpCallProxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * {@link HelpCallListView}のデフォルト実装です。
 * 
 * @author Yuhi Ishikura
 */
public class HelpCallListViewImpl extends AbstractTaskitView implements HelpCallListView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, HelpCallListViewImpl> {
    // empty
  }

  private Presenter presenter;
  @UiField(provided = true)
  CellTable<HelpCallListItem> table;
  @UiField
  Label messageLabel;
  @UiField
  Button checkInListButton;

  List<HelpCallListItem> listItems;
  Map<String, List<String>> studentToUsersMap;

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
  public void setHelpCalls(List<HelpCallProxy> helpCalls) {
    if (helpCalls.isEmpty()) {
      this.messageLabel.setText(getClientFactory().getMessages().nooneCallingMessage());
    } else {
      this.messageLabel.setText(getClientFactory().getMessages().callingMessage(String.valueOf(helpCalls.size())));
    }

    this.listItems = new ArrayList<HelpCallListItem>(helpCalls.size());
    for (HelpCallProxy call : helpCalls) {
      this.listItems.add(new HelpCallListItem(call));
    }

    updateListData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCheckMaps(Map<String, List<String>> studentToUsersMap) {
    this.studentToUsersMap = studentToUsersMap;
    updateListData();
  }

  /**
   * リストデータを更新します。
   * <p>
   * {@link #studentToUsersMap}がすでに取得されていれば、リストアイテムにそれを反映させます。
   */
  private void updateListData() {
    if (this.listItems == null) return;
    if (this.studentToUsersMap != null) {
      for (HelpCallListItem item : this.listItems) {
        List<String> usersInCharge = this.studentToUsersMap.get(item.getHelpCall().getCaller().getId());
        if (usersInCharge == null || usersInCharge.size() == 0) {
          item.setUsersInCharge(Collections.<String> emptyList());
        } else {
          item.setUsersInCharge(usersInCharge);
        }
      }
    }

    this.table.setRowData(this.listItems);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  static class HelpCallListItem {

    HelpCallProxy helpCall;
    List<String> usersInCharge = Collections.emptyList();

    HelpCallListItem(HelpCallProxy helpCall) {
      this.helpCall = helpCall;
    }

    HelpCallProxy getHelpCall() {
      return this.helpCall;
    }

    List<String> getUsersInCharge() {
      return this.usersInCharge;
    }

    void setUsersInCharge(List<String> usersInCharge) {
      this.usersInCharge = usersInCharge;
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.table = new CellTable<HelpCallListViewImpl.HelpCallListItem>();
    initColumns();
    listenSelectionEvent();

    final Widget widget = binder.createAndBindUi(this);
    this.checkInListButton.setText(getClientFactory().getMessages().checkInListLabel());

    return widget;
  }

  private void initColumns() {
    this.table.addColumn(new Column<HelpCallListViewImpl.HelpCallListItem, String>(new TextCell()) {

      final DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

      @Override
      public String getValue(HelpCallListItem object) {
        final Date date = object.getHelpCall().getDate();
        return this.format.format(date);
      }
    }, getClientFactory().getMessages().dateLabel());
    this.table.addColumn(new Column<HelpCallListItem, String>(new TextCell()) {

      @Override
      public String getValue(HelpCallListItem object) {
        return object.getHelpCall().getCaller().getId();
      }

    }, getClientFactory().getMessages().callerColumnLabel());
    this.table.addColumn(new Column<HelpCallListItem, String>(new TextCell()) {

      @Override
      public String getValue(HelpCallListItem object) {
        return object.getHelpCall().getMessage();
      }
    }, getClientFactory().getMessages().messageLabel());
    this.table.addColumn(new Column<HelpCallListItem, String>(new TextCell()) {

      @Override
      public String getValue(HelpCallListItem object) {
        List<String> usersInCharge = object.getUsersInCharge();
        if (usersInCharge == null) return getClientFactory().getMessages().notLoadedYetMessage();

        final StringBuilder userList = new StringBuilder();
        for (int i = 0; i < usersInCharge.size(); i++) {
          if (i != 0) userList.append(","); //$NON-NLS-1$
          userList.append(usersInCharge.get(i));
        }
        return userList.toString();
      }

    }, getClientFactory().getMessages().acceptingColumnLabel());

    Column<HelpCallListItem, String> locationButtonColumn = new Column<HelpCallListItem, String>(new ButtonCell()) {

      @Override
      public String getValue(@SuppressWarnings("unused") HelpCallListItem object) {
        return getClientFactory().getMessages().locationLabel();
      }

    };
    locationButtonColumn.setFieldUpdater(new FieldUpdater<HelpCallListViewImpl.HelpCallListItem, String>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void update(int index, HelpCallListItem object, @SuppressWarnings("unused") String value) {
        final int y = table.getRowElement(index).getAbsoluteBottom();
        final int x = table.getAbsoluteLeft() + table.getOffsetWidth();
        final String userId = object.getHelpCall().getCaller().getId();

        showUserMap(x, y, userId);
      }
    });
    this.table.addColumn(locationButtonColumn);
  }

  private static void showUserMap(final int x, final int y, String userId) {
    final Image image = new Image("roommap?id=" + userId); //$NON-NLS-1$
    final PopupPanel popup = new PopupPanel();
    popup.setAutoHideEnabled(true);
    popup.add(image);
    popup.setVisible(false);
    popup.show();
    image.addLoadHandler(new LoadHandler() {

      @Override
      public void onLoad(@SuppressWarnings("unused") LoadEvent event) {
        popup.setPopupPosition(x - image.getWidth(), y);
        popup.setVisible(true);
      }
    });
  }

  private void listenSelectionEvent() {
    // テーブルの選択変更を監視
    final SingleSelectionModel<HelpCallListItem> selectionModel = new SingleSelectionModel<HelpCallListItem>();
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSelectionChange(@SuppressWarnings("unused") SelectionChangeEvent event) {
        final HelpCallListItem selectedCall = selectionModel.getSelectedObject();
        HelpCallListViewImpl.this.presenter.helpCallSelected(selectedCall.getHelpCall());
      }
    });

    // テーブルの選択イベントを、選択されたのがボタンの場合は発生しないようにフィルタリング
    CellPreviewEvent.Handler<HelpCallListViewImpl.HelpCallListItem> cellPreviewHandler = new DefaultSelectionEventManager<HelpCallListViewImpl.HelpCallListItem>(null) {

      @Override
      public void onCellPreview(CellPreviewEvent<HelpCallListItem> event) {
        @SuppressWarnings("unqualified-field-access")
        Column<?, ?> column = table.getColumn(event.getColumn());
        if (column.getCell() instanceof ButtonCell) {
          return;
        }

        super.onCellPreview(event);
      }
    };
    this.table.setSelectionModel(selectionModel, cellPreviewHandler);
    this.table.addCellPreviewHandler(cellPreviewHandler);
  }
}
