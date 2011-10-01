/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreQuery;
import org.mklab.taskit.client.ui.StudentView;

import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class StudentActivity extends TaskitActivity {

  /**
   * {@link StudentActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget createTaskitView(ClientFactory clientFactory) {
    final StudentView studentView = clientFactory.getStudentView();
    final StudentScoreQuery query = new StudentScoreQuery(clientFactory.getRequestFactory());
    query.query(new StudentScoreQuery.Handler() {

      @Override
      public void handleResult(StudentScoreModel model) {
        studentView.setModel(model);
        StudentScoreModel.LectureScore latestScore = Util.getLatestScore(model);
        studentView.highlightRow(latestScore);
      }
    });
    return studentView.asWidget();
  }

}
