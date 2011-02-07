/**
 * 
 */
package org.mklab.taskit.client.activity;

import java.util.List;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.StudentScoreView;
import org.mklab.taskit.client.ui.StudentScoreView.Presenter;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.service.LectureService;
import org.mklab.taskit.shared.service.LectureServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public class StudentScoreActivity extends TaskitActivity implements Presenter {

  final LectureServiceAsync lectureService = GWT.create(LectureService.class);

  /**
   * {@link StudentScoreActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentScoreActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final Place place = clientFactory.getPlaceController().getWhere();
    if (place instanceof StudentScore == false) throw new IllegalStateException();

    final StudentScoreView view = clientFactory.getStudentScoreView();
    view.setPresenter(this);
    initialize(view);

    return view;
  }

  void initialize(final StudentScoreView view) {
    this.lectureService.getLectureCount(new AsyncCallback<Integer>() {

      @Override
      public void onSuccess(Integer result) {
        final int lectureCount = result.intValue();
        view.setLectureCount(lectureCount);
        if (lectureCount > 0) {
          fetchLectureData(view);
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught);
      }
    });
  }

  void fetchLectureData(final StudentScoreView view) {
    this.lectureService.getAllLectures(new AsyncCallback<LectureDto[]>() {

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught);
      }

      @Override
      public void onSuccess(LectureDto[] result) {
        for (int i = 0; i < result.length; i++) {
          LectureDto lecture = result[i];
          view.setLectureInfo(i, lecture.getReportCount(), lecture.getLecture().getTitle());
        }
      }
    });
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView.Presenter#onEvaluationChange(int,
   *      int, int)
   */
  @Override
  public void onEvaluationChange(int index, int no, int evaluation) {
    SC.say("Evaluation changed : " + String.valueOf(evaluation));
  }

}
