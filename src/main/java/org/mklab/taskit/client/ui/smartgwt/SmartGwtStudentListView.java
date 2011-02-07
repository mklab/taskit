/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentListView;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 5, 2011
 */
public class SmartGwtStudentListView extends AbstractTaskitView implements StudentListView {

  private Presenter presenter;
  private ListGrid grid;

  /**
   * {@link SmartGwtStudentListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public SmartGwtStudentListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.grid = new ListGrid();

    final ListGridField field = new ListGridField("userName", "User Name"); //$NON-NLS-1$ //$NON-NLS-2$
    this.grid.setFields(field);

    this.grid.setCellHeight(30);
    this.grid.setWidth100();
    this.grid.setHeight100();

    this.grid.addCellClickHandler(new CellClickHandler() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onCellClick(CellClickEvent event) {
        presenter.listDataClicked(event.getRowNum());
      }
    });
    return this.grid;
  }

  /**
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView#setListData(java.lang.String[])
   */
  @Override
  public void setListData(String[] listData) {
    ListGridRecord[] records = new ListGridRecord[listData.length];
    for (int i = 0; i < records.length; i++) {
      records[i] = new ListGridRecord();
      records[i].setAttribute("userName", listData[i]); //$NON-NLS-1$
    }
    this.grid.setData(records);
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView#setPresenter(org.mklab.taskit.client.ui.StudentListView.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    init();
  }

}
