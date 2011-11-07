/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.model.AttendanceListItem;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.UserProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * 講義別の全生徒の出席リストを表示するアクティビティです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private AttendanceListView view;
  private List<AttendanceProxy> attendances;
  private List<UserProxy> students;
  private List<LectureProxy> lectures;

  /**
   * {@link AttendanceListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AttendanceListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    this.view = clientFactory.getAttendanceListView();
    this.view.setPresenter(this);
    return this.view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();
    fetchLecturesAsync();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attend(final AccountProxy user, AttendanceType type) {
    final LectureProxy lecture = this.view.getSelectedLecture();
    getClientFactory().getRequestFactory().attendanceRequest().attend(user, lecture, type).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(getClientFactory().getMessages().submittedAttendanceMessage(user.getId(), lecture.getTitle()));
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorDialog(getClientFactory().getMessages().submittedAttendanceFailMessage(user.getId(), lecture.getTitle()) + ":" + error.getMessage()); //$NON-NLS-1$
      }

    });
  }

  /**
   * 現在選択中の講義の出席リストを非同期で取得しビューを更新します。
   */
  void updateListAsync() {
    final LectureProxy currentSelection = this.view.getSelectedLecture();
    updateListAsync(currentSelection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void lectureSelectionChanged(LectureProxy selectedLecture) {
    updateListAsync(selectedLecture);
  }

  private void updateListAsync(LectureProxy selectedLecture) {
    if (this.students == null) fetchStudentsAsync();
    fetchAttendancesByLectureAsync(selectedLecture);
  }

  private void fetchLecturesAsync() {
    showInformationMessage(getClientFactory().getMessages().fetchingLectureListMessage());
    getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.LECTURE_LIST, new Receiver<List<LectureProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<LectureProxy> arg0) {
        showInformationMessage(getClientFactory().getMessages().fetchedLectureListMessage());
        AttendanceListActivity.this.lectures = arg0;
        updateLectures();
        selectLatestLecture();
      }

    });
  }

  private void selectLatestLecture() {
    if (this.lectures == null) return;

    final LectureProxy latest = Util.getLatestLecture(this.lectures);
    if (latest == null) return;

    this.view.setSelectedLecture(latest);
  }

  private void fetchStudentsAsync() {
    showInformationMessage(getClientFactory().getMessages().fetchingUserListMessage());
    getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.STUDENT_LIST, new Receiver<List<UserProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<UserProxy> response) {
        showInformationMessage(getClientFactory().getMessages().fetchedUserListMessage());
        AttendanceListActivity.this.students = response;
        updateAttendanceList();
      }

    });
  }

  private void fetchAttendancesByLectureAsync(LectureProxy lecture) {
    showInformationMessage(getClientFactory().getMessages().fetchingAttendancesMessage());
    getClientFactory().getRequestFactory().attendanceRequest().getAttendancesByLecture(lecture).with("attender").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            showInformationMessage(getClientFactory().getMessages().fetchedAttendancesMessage());
            AttendanceListActivity.this.attendances = response;
            updateAttendanceList();
          }
        });
  }

  private void updateLectures() {
    this.view.setLectures(this.lectures);
  }

  private void updateAttendanceList() {
    if (this.attendances == null || this.students == null) return;

    final Map<String, AttendanceProxy> idToAttendance = new HashMap<String, AttendanceProxy>();
    for (AttendanceProxy a : this.attendances) {
      idToAttendance.put(a.getAttender().getId(), a);
    }

    final List<AttendanceListItem> listItems = new ArrayList<AttendanceListItem>();
    for (UserProxy user : this.students) {
      AttendanceListItem listItem = new AttendanceListItem(user, idToAttendance.get(user.getAccount().getId()));
      listItems.add(listItem);
    }
    this.view.setAttendances(listItems);
  }
}
