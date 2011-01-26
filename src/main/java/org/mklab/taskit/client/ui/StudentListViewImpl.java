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

  private FlexTable table;
  private static final int MAXIMUM_ROW_COUNT = 20;

  /**
   * {@link StudentListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.table = new FlexTable();
    this.table.setBorderWidth(1);
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
}
