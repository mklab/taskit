/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class StudentListViewImpl extends AbstractTaskitView implements StudentListView {

  private MultiColumnTable table;
  private Presenter presenter;

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
    this.table = new MultiColumnTable() {

      @Override
      void initTableBase(@SuppressWarnings("hiding") FlexTable table) {
        table.setBorderWidth(1);
        table.setText(0, 0, getClientFactory().getMessages().studentNoLabel());
      }

    };
    this.table.setColumnHeaderRows(1);
    this.table.addCellClickListener(new MultiColumnTable.CellClickListener() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void cellClicked(int row, int column) {
        final String clickedData = table.getText(row, column);
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
    this.table.clearTables();
    for (int i = 0; i < listData.length; i++) {
      this.table.setText(i, 0, listData[i]);
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
