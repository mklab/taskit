/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AttendanceListItem;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.AttendanceListViewImpl;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.dto.AttendanceBaseDto;
import org.mklab.taskit.shared.dto.AttendanceDto;
import org.mklab.taskit.shared.service.AttendanceService;
import org.mklab.taskit.shared.service.AttendanceServiceAsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private AttendanceListView view;
  private AttendanceServiceAsync service = GWT.create(AttendanceService.class);
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
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected Widget createTaskitView(ClientFactory clientFactory) {
    this.view = new AttendanceListViewImpl(clientFactory);
    this.view.setPresenter(this);

    this.updateTimer = new Timer() {

      @Override
      public void run() {}
    };
    this.updateTimer.scheduleRepeating(10 * 1000);

    return this.view.asWidget();
  }

  /**
   * @see com.google.gwt.activity.shared.AbstractActivity#onStop()
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

  private void fetchStudentsAsync() {
    getClientFactory().getRequestFactory().userRequest().getAllStudents().with("account").fire(new Receiver<List<UserProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<UserProxy> response) {
            AttendanceListActivity.this.students = response;
            updateListData();
          }

        });
  }

  private void fetchAttendancesByLectureAsync(LectureProxy lecture) {
    getClientFactory().getRequestFactory().attendanceRequest().getAttendancesByLecture(lecture).with("attender").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            AttendanceListActivity.this.attendances = response;
            updateListData();
          }
        });
  }

  private void updateListData() {
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
