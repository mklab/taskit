/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.EvaluationTableModel;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;
import org.mklab.taskit.shared.UserProxy;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class CwStudentListView extends AbstractTaskitView implements StudentListView {

  @UiField(provided = true)
  CellList<UserProxy> list;
  @UiField
  StudentScorePanel panel;
  private Presenter presenter;
  private static final Binder binder = GWT.create(Binder.class);

  private List<LectureProxy> lectures;
  private List<SubmissionProxy> submissions;
  private List<AttendanceProxy> attendances;

  interface Binder extends UiBinder<Widget, CwStudentListView> {
    // empty
  }

  /**
   * {@link CwStudentListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwStudentListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(List<UserProxy> listData) {
    this.list.setRowData(listData);
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
    final Cell<UserProxy> userCell = new AbstractCell<UserProxy>() {

      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, UserProxy value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div style='font-size:2em;'>"); //$NON-NLS-1$
        sb.appendHtmlConstant(value.getAccount().getId());
        sb.appendHtmlConstant("</div>"); //$NON-NLS-1$
      }

    };
    this.list = new CellList<UserProxy>(userCell);
    final SingleSelectionModel<UserProxy> selectionModel = new SingleSelectionModel<UserProxy>();
    this.list.setSelectionModel(selectionModel);
    this.list.getSelectionModel().addSelectionChangeHandler(new Handler() {

      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        final UserProxy selected = selectionModel.getSelectedObject();
        selectionChanged(selected);
      }
    });

    return binder.createAndBindUi(this);
  }

  void selectionChanged(UserProxy selectedUser) {
    this.panel.clearScoreData();

    final String selectedAccountId = selectedUser.getAccount().getId();
    this.submissions = null;
    this.attendances = null;

    fetchLectures();
    fetchSubmissions(selectedAccountId);
    fetchAttendances(selectedAccountId);
  }

  private void fetchLectures() {
    final LectureRequest lectureRequest = getClientFactory().getRequestFactory().lectureRequest();
    lectureRequest.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<LectureProxy> response) {
            lectures = response;
            updateScorePanel();
          }

        });
  }

  private void fetchAttendances(final String selectedAccountId) {
    final AttendanceRequest attendanceRequest = getClientFactory().getRequestFactory().attendanceRequest();
    attendanceRequest.getAllAttendancesByAccountId(selectedAccountId).with("lecture").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            attendances = response;
            updateScorePanel();
          }

        });
  }

  private void fetchSubmissions(final String selectedAccountId) {
    final SubmissionRequest submissionRequest = getClientFactory().getRequestFactory().submissionRequest();
    submissionRequest.getSubmissionsByAccountId(selectedAccountId).with("report.lecture").fire(new Receiver<List<SubmissionProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<SubmissionProxy> response) {
            submissions = response;
            updateScorePanel();
          }

        });
  }

  private void updateScorePanel() {
    if (this.lectures == null || this.attendances == null || this.submissions == null) {
      return;
    }
    EvaluationTableModel model = new EvaluationTableModel(this.lectures, this.attendances, this.submissions);
    this.panel.setModel(model);
  }
}
