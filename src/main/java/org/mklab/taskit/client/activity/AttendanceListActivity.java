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
import org.mklab.taskit.shared.model.Lecture;

import com.google.gwt.user.client.Window;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendanceListActivity extends TaskitActivity implements AttendanceListView.Presenter {

  private final String[] choosableAttendenceType;

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
    final AttendanceListView view = new AttendanceListViewImpl(clientFactory);
    view.setAttendanceTypes(this.choosableAttendenceType);
    view.setPresenter(this);
    //    view.setLectures(null);

    final String[] sample = new String[100];
    for (int i = 0; i < sample.length; i++) {
      sample[i] = String.valueOf(i + 10675001);
    }

    for (int i = 0; i < 100; i++) {
      view.setStudentNumber(i, String.valueOf(i + 10675003));
      view.setAttendanceType(i, (int)(Math.random() * 4));
    }

    return view;
  }

  void fetchUserNames() {

  }

  void fetchLectureCount() {

  }

  void fetchAttendanceDtoFromLecture(int lectureIndex) {

  }

  void updateAttendanceState(int userIndex, int lectureIndex, int attendanceType) {

  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#attendanceTypeEditted(int,
   *      int)
   */
  @Override
  public void attendanceTypeEditted(int index, int attendanceTypeIndex) {
    //final int selectedLectureIndex = this.
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#lectureSelectionChanged(int)
   */
  @Override
  public void lectureSelectionChanged(int selectedLectureIndex) {
    // TODO Auto-generated method stub

  }

  /**
   * @see org.mklab.taskit.client.ui.AttendanceListView.Presenter#studentNumberClicked(int)
   */
  @Override
  public void studentNumberClicked(int index) {
    getClientFactory().getPlaceController().goTo(new StudentScore(index));
  }

}
