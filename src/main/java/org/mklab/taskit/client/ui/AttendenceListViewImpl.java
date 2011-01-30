/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendenceListViewImpl extends AbstractTaskitView implements AttendenceListView {

  private Presenter presenter;
  private FlexTable table;

  /**
   * {@link AttendenceListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AttendenceListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView#setPresenter(org.mklab.taskit.client.ui.AttendenceListView.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView#setAttendenceTypes(java.lang.String[])
   */
  @Override
  public void setAttendenceTypes(String[] types) {

  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    final String ATTENDENCE_GROUP_NAME = "attendence";
    final RadioButton circle = new RadioButton(ATTENDENCE_GROUP_NAME, "○");
    final RadioButton triangle = new RadioButton(ATTENDENCE_GROUP_NAME, "△");
    final RadioButton cross = new RadioButton(ATTENDENCE_GROUP_NAME, "×");

    this.table = new FlexTable();
    this.table.setBorderWidth(1);
    this.table.setText(0, 0, "Student No.");
    this.table.setText(0, 1, "Attendence State");
    this.table.getFlexCellFormatter().setColSpan(0, 1, 3);
    this.table.setText(1, 0, "10675003");
    this.table.setWidget(1, 1, circle);
    this.table.setWidget(1, 2, triangle);
    this.table.setWidget(1, 3, cross);

    return this.table;
  }

}
