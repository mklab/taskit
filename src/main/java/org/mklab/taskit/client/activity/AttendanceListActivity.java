/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
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

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private AttendanceListView view;
  private List<AttendanceProxy> attendances;
  private List<UserProxy> students;
  private List<LectureProxy> lectures;
  private Timer updateTimer;

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

    fetchLecturesAsync();

    this.updateTimer = new Timer() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void run() {
        updateListAsync();
      }
    };
    this.updateTimer.scheduleRepeating(30 * 1000);

    return this.view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStop() {
    if (this.updateTimer != null) {
      this.updateTimer.cancel();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void attend(AccountProxy user, AttendanceType type) {
    final LectureProxy lecture = this.view.getSelectedLecture();
    getClientFactory().getRequestFactory().attendanceRequest().attend(user, lecture, type).fire(new Receiver<Void>() {

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

  private void updateListAsync() {
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
    getClientFactory().getRequestFactory().lectureRequest().getAllLectures().fire(new Receiver<List<LectureProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<LectureProxy> arg0) {
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
    getClientFactory().getRequestFactory().userRequest().getAllStudents().with("account").fire(new Receiver<List<UserProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<UserProxy> response) {
            AttendanceListActivity.this.students = response;
            updateAttendanceList();
          }

        });
  }

  private void fetchAttendancesByLectureAsync(LectureProxy lecture) {
    getClientFactory().getRequestFactory().attendanceRequest().getAttendancesByLecture(lecture).with("attender").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
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
