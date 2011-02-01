/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.AttendanceListViewImpl;
import org.mklab.taskit.client.ui.TaskitView;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private final String[] choosableAttendenceType;
  private AttendanceListView view;

  /**
   * {@link AttendanceListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AttendanceListActivity(ClientFactory clientFactory) {
    super(clientFactory);
    final Messages m = clientFactory.getMessages();
    this.choosableAttendenceType = new String[] {m.attendedLabel(), m.absentLabel(), m.illnessLabel(), m.authorizedAbsenceLabel()};
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    this.view = new AttendanceListViewImpl(clientFactory);
    this.view.setAttendanceTypes(this.choosableAttendenceType);
    this.view.setPresenter(this);
    //    view.setLectures(null);

    fetchUserNames();

    return this.view;
  }

  void fetchUserNames() {
    
  }

  void fetchLectureCount() {

  }

  void fetchAttendanceDtoFromLecture(int lectureIndex) {

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
