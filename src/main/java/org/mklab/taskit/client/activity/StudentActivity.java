/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordQuery;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class StudentActivity extends TaskitActivity implements StudentView.Presenter {

  private Timer helpCallPositionUpdater;

  /**
   * {@link StudentActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentActivity(ClientFactory clientFactory) {
    super(clientFactory);
    this.helpCallPositionUpdater = new Timer() {

      @Override
      public void run() {
        updateHelpCallPosition();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final StudentView studentView = clientFactory.getStudentView();
    studentView.setPresenter(this);

    return studentView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();

    final StudentwiseRecordQuery query = new StudentwiseRecordQuery(getClientFactory());
    final StudentView studentView = (StudentView)getTaskitView();

    showInformationMessage(getClientFactory().getMessages().fetchingUserRecordsMessage(getLoginUser().getAccount().getId()));
    query.query(new StudentwiseRecordQuery.Handler() {

      @Override
      public void handleResult(StudentwiseRecordModel model) {
        showInformationMessage(getClientFactory().getMessages().fetchedUserRecordsMessage(getLoginUser().getAccount().getId()));
        studentView.setModel(model);
        StudentwiseRecordModel.LectureScore latestScore = Util.getLatestScore(model);
        studentView.highlightRow(latestScore);
      }
    });
    getClientFactory().getRequestFactory().helpCallRequest().isCalling().fire(new Receiver<Boolean>() {

      @Override
      public void onSuccess(Boolean response) {
        showInformationMessage(getClientFactory().getMessages().fetchedHelpCallState());
        setCalling(response.booleanValue());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStop() {
    super.onStop();
    this.helpCallPositionUpdater.cancel();
  }

  void setCalling(boolean calling) {
    ((StudentView)getTaskitView()).setCalling(calling);
    if (calling) {
      updateHelpCallPosition();
      this.helpCallPositionUpdater.scheduleRepeating(5 * 1000);
    } else {
      this.helpCallPositionUpdater.cancel();
    }
  }

  void updateHelpCallPosition() {
    getClientFactory().getRequestFactory().helpCallRequest().getPosition().fire(new Receiver<Long>() {

      @Override
      public void onSuccess(Long response) {
        ((StudentView)getTaskitView()).setHelpCallPosition(response.intValue());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void call(String message) {
    getClientFactory().getRequestFactory().helpCallRequest().call(message).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        setCalling(true);
        showInformationDialog(getClientFactory().getMessages().callingNowMessage());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        setCalling(false);
        showErrorDialog(getClientFactory().getMessages().calledFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
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
        setCalling(false);
        showInformationDialog(getClientFactory().getMessages().uncalledMyselfMessage());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        setCalling(true);
        showErrorDialog(getClientFactory().getMessages().uncalledMyselfFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
      }

    });
  }

}
