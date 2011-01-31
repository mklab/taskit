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
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private AttendanceListView view;
  private AttendanceServiceAsync service = GWT.create(AttendanceService.class);

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
        final List<String> userNames = result.getUserNames();
        view.setLectures(result.getLectureCount());
        view.setAttendanceTypes(result.getAttendanceTypes());
        for (int i = 0; i < userNames.size(); i++) {
          view.setStudentNumber(i, userNames.get(i));
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught.toString());
      }

    });
  }

  void fetchAttendanceDtoFromLecture(int lectureIndex) {
    final AttendanceDto data = null;
    final int[] attendanceTypes = data.getAttendances();

  }

  void updateAttendanceState(int userIndex, int lectureIndex, int attendanceTypeIndex) {

  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#attendanceTypeEditted(int,
   *      int)
   */
  @Override
  public void attendanceTypeEditted(int index, int attendanceTypeIndex) {
    final int lectureIndex = this.view.getSelectedLecture();
    updateAttendanceState(index, lectureIndex, attendanceTypeIndex);
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
