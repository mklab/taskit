/**
 * 
 */
package org.mklab.taskit.client.ui;

import java.util.List;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListViewImpl extends AbstractTaskitView implements AttendanceListView {

  /** 出席タイプ選択のoptionのグループ名です。 */
  public static final String ATTENDENCE_GROUP_NAME = "attendence"; //$NON-NLS-1$
  private Presenter presenter;
  private MultiColumnTable table;
  private List<String> attendanceTypes;
  private ListBox lectureListBox;

  /**
   * {@link AttendanceListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AttendanceListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    final VerticalPanel vPanel = new VerticalPanel();
    this.lectureListBox = new ListBox();
    this.lectureListBox.addChangeHandler(new ChangeHandler() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onChange(@SuppressWarnings("unused") ChangeEvent event) {
        presenter.lectureSelectionChanged(lectureListBox.getSelectedIndex());
      }
    });
    this.table = new MultiColumnTable() {

      @SuppressWarnings("synthetic-access")
      @Override
      void initTableBase(@SuppressWarnings("hiding") FlexTable table) {
        final Messages m = getClientFactory().getMessages();
        table.setBorderWidth(1);
        table.setText(1, 0, m.studentNoLabel());

        table.setText(0, 1, m.attendenceTypeLabel());
        table.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
        final List<String> options = AttendanceListViewImpl.this.attendanceTypes;
        if (options != null) {
          for (int i = 0; i < options.size(); i++) {
            table.setText(1, i + 1, options.get(i));
          }
          table.getFlexCellFormatter().setColSpan(0, 1, options.size());
        }
      }
    };
    this.table.setColumnHeaderRows(2);
    this.table.addCellClickListener(new MultiColumnTable.CellClickListener() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void cellClicked(int row, int column) {
        if (column == 0) {
          presenter.studentNumberClicked(row);
          return;
        }
        presenter.attendanceTypeEditted(table.getText(row, 0), column - 1);
      }
    });

    vPanel.add(this.lectureListBox);
    vPanel.add(this.table);

    return vPanel;
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setPresenter(org.mklab.taskit.client.ui.AttendanceListView.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setStudentNumber(int,
   *      java.lang.String)
   */
  @Override
  public void setStudentNumber(int index, String studentNo) {
    this.table.setText(index, 0, studentNo);
    for (int i = 0; i < this.attendanceTypes.size(); i++) {
      this.table.setWidget(index, i + 1, new RadioButton(ATTENDENCE_GROUP_NAME + studentNo));
    }
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setAttendanceType(int,
   *      int)
   */
  @Override
  public void setAttendanceType(int index, int attendenceTypeIndex) {
    final RadioButton bt = (RadioButton)this.table.getWidget(index, attendenceTypeIndex + 1);
    bt.setValue(Boolean.TRUE);
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setLectures(int)
   */
  @Override
  public void setLectures(int lectureCount) {
    this.lectureListBox.clear();
    final Messages messages = getClientFactory().getMessages();

    for (int i = 0; i < lectureCount; i++) {
      this.lectureListBox.addItem(messages.lectureIndexLabel(String.valueOf(i + 1)));

    }
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setAttendanceTypes(List)
   */
  @Override
  public void setAttendanceTypes(List<String> attendanceTypes) {
    this.attendanceTypes = attendanceTypes;
    this.table.clearTables();
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#getSelectedLecture()
   */
  @Override
  public int getSelectedLecture() {
    return this.lectureListBox.getSelectedIndex();
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setSelectedLecture(int)
   */
  @Override
  public void setSelectedLecture(int index) {
    this.lectureListBox.setSelectedIndex(index);
  }

}
