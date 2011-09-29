/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.EvaluationTableModel;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class StudentListActivity extends TaskitActivity implements StudentListView.Presenter {

  private StudentListView view;
  private List<LectureProxy> lectures;
  private List<SubmissionProxy> submissions;
  private List<AttendanceProxy> attendances;
  private EvaluationTableModel model;

  /**
   * {@link StudentListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected Widget createTaskitView(ClientFactory clientFactory) {
    final StudentListView list = clientFactory.getStudentListView();
    this.view = list;
    list.setPresenter(this);

    getClientFactory().getRequestFactory().userRequest().getAllStudents().with("account").fire(new Receiver<List<UserProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<UserProxy> arg0) {
            final String[] names = new String[arg0.size()];
            for (int i = 0; i < names.length; i++) {
              names[i] = arg0.get(i).getAccount().getId();
            }
            list.setListData(arg0);
          }

        });
    return list.asWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void submit(ReportProxy report, int point) {
    final UserProxy user = this.view.getSelectedUser();
    final SubmissionRequest submissionRequest = getClientFactory().getRequestFactory().submissionRequest();
    submissionRequest.submit(user.getAccount(), report, point).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
      }

    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attend(LectureProxy lecture, AttendanceType type) {
    final UserProxy user = this.view.getSelectedUser();
    final AttendanceRequest attendanceRequest = getClientFactory().getRequestFactory().attendanceRequest();
    attendanceRequest.attend(user.getAccount(), lecture, type).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
      }

    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void listSelectionChanged(UserProxy selectedUser) {
    this.submissions = null;
    this.attendances = null;
    this.model = null;

    this.view.clearUserPage();
    fetchAndShowAsync(selectedUser);
  }

  private void fetchAndShowAsync(UserProxy selectedUser) {
    if (this.lectures == null) fetchLectures(selectedUser);

    fetchAttendances(selectedUser);
    fetchSubmissions(selectedUser);
  }

  private void fetchLectures(final UserProxy user) {
    final LectureRequest lectureRequest = getClientFactory().getRequestFactory().lectureRequest();
    lectureRequest.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

          @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
          @Override
          public void onSuccess(List<LectureProxy> response) {
            lectures = response;
            showUserPageIfPossible(user);
          }

        });
  }

  private void fetchAttendances(final UserProxy user) {
    final String accountId = user.getAccount().getId();
    final AttendanceRequest attendanceRequest = getClientFactory().getRequestFactory().attendanceRequest();
    attendanceRequest.getAllAttendancesByAccountId(accountId).with("lecture").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            attendances = response;
            showUserPageIfPossible(user);
          }

        });
  }

  private void fetchSubmissions(final UserProxy user) {
    final String accountId = user.getAccount().getId();
    final SubmissionRequest submissionRequest = getClientFactory().getRequestFactory().submissionRequest();
    submissionRequest.getSubmissionsByAccountId(accountId).with("report.lecture").fire(new Receiver<List<SubmissionProxy>>() { //$NON-NLS-1$

          @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
          @Override
          public void onSuccess(List<SubmissionProxy> response) {
            submissions = response;
            showUserPageIfPossible(user);
          }

        });
  }

  private void showUserPageIfPossible(UserProxy user) {
    if (this.lectures == null || this.attendances == null || this.submissions == null) {
      return;
    }
    this.model = new EvaluationTableModel(this.lectures, this.attendances, this.submissions);
    this.view.showUserPage(user, this.model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reloadUserPage() {
    this.view.clearUserPage();
    final UserProxy selectedUser = this.view.getSelectedUser();
    fetchAndShowAsync(selectedUser);
  }

}
