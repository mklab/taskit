/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreQuery;
import org.mklab.taskit.client.ui.StudentView;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class StudentActivity extends TaskitActivity implements StudentView.Presenter {

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
    studentView.setPresenter(this);

    final StudentScoreQuery query = new StudentScoreQuery(clientFactory.getRequestFactory());
    query.query(new StudentScoreQuery.Handler() {

      @Override
      public void handleResult(StudentScoreModel model) {
        studentView.setModel(model);
        StudentScoreModel.LectureScore latestScore = Util.getLatestScore(model);
        studentView.highlightRow(latestScore);
      }
    });
    clientFactory.getRequestFactory().helpCallRequest().isCalling().fire(new Receiver<Boolean>() {

      @Override
      public void onSuccess(Boolean response) {
        studentView.setCalling(response.booleanValue());
      }
    });
    return studentView.asWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void call(String message) {
    getClientFactory().getRequestFactory().helpCallRequest().call(message).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage("Called successfully."); //$NON-NLS-1$
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        reload();
        showInformationMessage("Failed to call. : " + error.getMessage()); //$NON-NLS-1$
      }

    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void uncall() {
    getClientFactory().getRequestFactory().helpCallRequest().uncall().fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage("Uncalled successfully."); //$NON-NLS-1$
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        reload();
        showInformationMessage("Failed to uncall. : " + error.getMessage()); //$NON-NLS-1$
      }

    });
  }

}
