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

  /** 出席タイプ選択のoptionのグループ名です。 */
  public static final String ATTENDENCE_GROUP_NAME = "attendence"; //$NON-NLS-1$
  private Presenter presenter;
  private MultiColumnTable table;

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
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView#setStudentNumber(int,
   *      java.lang.String)
   */
  @Override
  public void setStudentNumber(int index, String studentNo) {
    this.table.setText(index, 0, studentNo);
    for (int i = 0; i < this.presenter.getChoosableAttendenceTypes().length; i++) {
      this.table.setWidget(index, i + 1, new RadioButton(ATTENDENCE_GROUP_NAME + studentNo));
    }
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView#setAttendenceType(int,
   *      int)
   */
  @Override
  public void setAttendenceType(int index, int attendenceTypeIndex) {
    final RadioButton bt = (RadioButton)this.table.getWidget(index, attendenceTypeIndex + 1);
    bt.setValue(Boolean.TRUE);
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
        table.setText(1, 0, "Student No.");
        table.setText(0, 1, "Attendence State");
        final String[] options = presenter.getChoosableAttendenceTypes();
        for (int i = 0; i < options.length; i++) {
          table.setText(1, i + 1, options[i]);
        }
        table.getFlexCellFormatter().setColSpan(0, 1, options.length);
      }
    };
    this.table.setColumnHeaderRows(2);

    return this.table;
  }

}
