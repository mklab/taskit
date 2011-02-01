/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.model.Lecture;

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
  private String[] attendanceTypes;
  private ListBox lectureListBox;
  private Lecture[] lectures;

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
    this.table = new MultiColumnTable() {

      @SuppressWarnings("synthetic-access")
      @Override
      void initTableBase(@SuppressWarnings("hiding") FlexTable table) {
        final Messages m = getClientFactory().getMessages();
        table.setBorderWidth(1);
        table.setText(1, 0, m.studentNoLabel());

        table.setText(0, 1, m.attendenceTypeLabel());
        table.getFlexCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
        final String[] options = AttendanceListViewImpl.this.attendanceTypes;
        for (int i = 0; i < options.length; i++) {
          table.setText(1, i + 1, options[i]);
        }
        table.getFlexCellFormatter().setColSpan(0, 1, options.length);
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
        presenter.attendanceTypeEditted(row, column - 1);
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
    for (int i = 0; i < this.attendanceTypes.length; i++) {
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
   * @see org.mklab.taskit.client.ui.AttendanceListView#setLectures(org.mklab.taskit.shared.model.Lecture[])
   */
  @Override
  public void setLectures(Lecture[] lecture) {
    this.lectures = lecture;
    this.lectureListBox.clear();
    final Messages messages = getClientFactory().getMessages();

    for (int i = 0; i < lecture.length; i++) {
      this.lectureListBox.addItem(messages.lectureIndexLabel(String.valueOf(i + 1)));

    }
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setAttendanceTypes(java.lang.String[])
   */
  @Override
  public void setAttendanceTypes(String[] attendanceTypes) {
    if (this.table != null) throw new IllegalStateException();

    this.attendanceTypes = attendanceTypes;
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView#setSelectedLecture(org.mklab.taskit.shared.model.Lecture)
   */
  @Override
  public void setSelectedLecture(Lecture lecture) {
    int index = -1;
    for (int i = 0; i < this.lectures.length; i++) {
      if (this.lectures[i] == lecture) {
        index = i;
      }
    }
    if (index == -1) return;
    this.lectureListBox.setSelectedIndex(index);
  }

}
