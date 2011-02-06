/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.StudentScoreView;
import org.mklab.taskit.client.ui.StudentScoreView.Presenter;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.place.shared.Place;
import com.smartgwt.client.util.SC;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public class StudentScoreActivity extends TaskitActivity implements Presenter {

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

    final int accountId = ((StudentScore)place).getAccountId();
    final StudentScoreView view = clientFactory.getStudentScoreView();
    view.setPresenter(this);
    view.setLectureCount(5);
    for (int i = 0; i < 5; i++) {
      view.setLectureInfo(i, 3, String.valueOf(i + 1));
      for (int j = 0; j < 3; j++) {
        view.setEvaluation(i, j, (int)(Math.random() * 100));
      }
    }
    return view;
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
