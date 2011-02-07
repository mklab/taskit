/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.StudentScoreView;
import org.mklab.taskit.client.ui.StudentScoreView.Presenter;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.dto.StudentwiseScoresDto;
import org.mklab.taskit.shared.service.LectureService;
import org.mklab.taskit.shared.service.LectureServiceAsync;
import org.mklab.taskit.shared.service.SubmissionService;
import org.mklab.taskit.shared.service.SubmissionServiceAsync;

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

  final SubmissionServiceAsync submissionService = GWT.create(SubmissionService.class);
  private String userName;

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

    this.userName = ((StudentScore)place).getUserName();
    final StudentScoreView view = clientFactory.getStudentScoreView();
    view.setPresenter(this);

    initialize(view);

    return view;
  }

  void initialize(final StudentScoreView view) {
    this.submissionService.getStudentwiseScores(this.userName, new AsyncCallback<StudentwiseScoresDto>() {

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught);
      }

      @Override
      public void onSuccess(StudentwiseScoresDto result) {
        view.setLectureCount(result.getLectureCount());
        for (int i = 0; i < result.getLectureCount(); i++) {
          final int reportCount = result.getLecture(i).getReportCount();
          view.setLectureInfo(i, reportCount, result.getLecture(i).getLecture().getTitle());
          for (int j = 0; j < reportCount; j++) {
            view.setEvaluation(i, j, result.getScoreTable().getScore(i, j));
          }
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
    this.submissionService.setEvaluation(this.userName, index, no, evaluation, new AsyncCallback<Void>() {

      @Override
      public void onFailure(Throwable caught) {
        showErrorMessage(caught);
      }

      @SuppressWarnings("unused")
      @Override
      public void onSuccess(Void result) {
        // do nothing
      }

    });
  }
}
