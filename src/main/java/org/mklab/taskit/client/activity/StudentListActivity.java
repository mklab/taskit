/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreQuery;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
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
  private StudentScoreQuery query;

  /**
   * {@link StudentListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListActivity(ClientFactory clientFactory) {
    super(clientFactory);
    this.query = new StudentScoreQuery(clientFactory.getRequestFactory());
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
    this.view.clearUserPage();
    fetchAndShowAsync(selectedUser);
  }

  private void fetchAndShowAsync(final UserProxy selectedUser) {
    this.query.query(selectedUser.getAccount().getId(), new StudentScoreQuery.Handler() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void handleResult(StudentScoreModel model) {
        view.showUserPage(selectedUser, model);

        final StudentScoreModel.LectureScore latestRow = Util.getLatestScore(model);
        view.highlightRow(latestRow);
      }
    });
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
