/**
 * 
 */
package org.mklab.taskit.client.activity;

import java.util.List;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.AttendanceListViewImpl;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.dto.AttendanceBaseDto;
import org.mklab.taskit.shared.dto.AttendanceDto;
import org.mklab.taskit.shared.service.AttendanceService;
import org.mklab.taskit.shared.service.AttendanceServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private AttendanceListView view;
  private AttendanceServiceAsync service = GWT.create(AttendanceService.class);
  private List<String> attendanceTypes;
  private List<String> userNames;

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
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    this.view = new AttendanceListViewImpl(clientFactory);
    this.view.setPresenter(this);

    fetchInitialData();

    return this.view;
  }

  void fetchInitialData() {
    this.service.getBaseData(new AsyncCallback<AttendanceBaseDto>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onSuccess(AttendanceBaseDto result) {
        userNames = result.getUserNames();
        final int lectureCount = result.getLectureCount();
        attendanceTypes = result.getAttendanceTypes();
        view.setLectures(lectureCount);

        view.setAttendanceTypes(attendanceTypes);
        for (int i = 0; i < userNames.size(); i++) {
          view.setStudentNumber(i, userNames.get(i));
        }
        if (lectureCount > 0) fetchAttendanceDtoFromLecture(0);
      }

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught.toString());
      }

    });
  }

  void fetchAttendanceDtoFromLecture(int lectureIndex) {
    this.service.getLecturewiseAttendanceData(lectureIndex, new AsyncCallback<AttendanceDto>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onSuccess(AttendanceDto result) {
        for (int i = 0; i < userNames.size(); i++) {
          final String userName = userNames.get(i);
          final int index = result.getAttendanceTypeIndex(userName);
          view.setAttendanceType(i, index == -1 ? 1 : index);
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught.toString());
      }

    });
  }

  void updateAttendanceState(String userName, int lectureIndex, int attendanceTypeIndex) {
    this.service.setAttendanceType(userName, lectureIndex, this.attendanceTypes.get(attendanceTypeIndex), new AsyncCallback<Void>() {

      /**
       * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
       */
      @Override
      public void onSuccess(Void result) {
        // do nothing
      }

      /**
       * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
       */
      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught.toString());
      }
    });
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#attendanceTypeEditted(java.lang.String,
   *      int)
   */
  @Override
  public void attendanceTypeEditted(String userName, int attendanceTypeIndex) {
    final int lectureIndex = this.view.getSelectedLecture();
    updateAttendanceState(userName, lectureIndex, attendanceTypeIndex);
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#lectureSelectionChanged(int)
   */
  @Override
  public void lectureSelectionChanged(int selectedLectureIndex) {
    fetchAttendanceDtoFromLecture(selectedLectureIndex);
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#studentNumberClicked(int)
   */
  @Override
  public void studentNumberClicked(int index) {
    getClientFactory().getPlaceController().goTo(new StudentScore(index));
  }

}
