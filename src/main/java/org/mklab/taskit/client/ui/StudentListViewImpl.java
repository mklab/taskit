/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class StudentListViewImpl extends AbstractTaskitView implements StudentListView {

  private FlexTable table;
  private Presenter presenter;
  private static final int MAXIMUM_ROW_COUNT = 20;

  /**
   * {@link StudentListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.table = new FlexTable();
    this.table.setBorderWidth(1);
    this.table.addClickHandler(new ClickHandler() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void onClick(ClickEvent event) {
        final Cell cell = table.getCellForEvent(event);
        if (cell == null) return;

        final String clickedData = table.getText(cell.getRowIndex(), cell.getCellIndex());
        presenter.listDataClicked(clickedData);
      }
    });
    return this.table;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView#setListData(java.lang.String[])
   */
  @Override
  public void setListData(String[] listData) {
    this.table.clear();
    int column = 0;
    int row = 0;
    for (int i = 0; i < listData.length; i++) {
      this.table.setText(row, column, listData[i]);
      row++;
      if (row >= MAXIMUM_ROW_COUNT) {
        row = 0;
        column++;
      }
    }
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView#setPresenter(org.mklab.taskit.client.ui.StudentListView.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }
}
