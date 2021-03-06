/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.event.TimeoutRecoveryFailureException;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordQuery;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.RecordProxy;
import org.mklab.taskit.shared.event.MyHelpCallEvent;
import org.mklab.taskit.shared.event.MyRecordChangeEvent;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.novanic.eventservice.client.event.Event;


/**
 * 学生用のアクティビティです。
 * <p>
 * 自分の成績の確認、及びTAの呼び出しを行うことが出来ます。
 * 
 * @author Yuhi Ishikura
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
  public void apply(Event evt) {
    super.apply(evt);
    if (evt instanceof MyHelpCallEvent) {
      updateHelpCallStateAsync();
    } else if (evt instanceof MyRecordChangeEvent) {
      updateRecordAsync();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recoverFromTimeout() throws TimeoutRecoveryFailureException {
    updateHelpCallStateAsync();
    updateRecordAsync();
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

    ((StudentView)getTaskitView()).setLoginUser(getLoginUser());
    updateRecordAsync();
    updateHelpCallStateAsync();
  }

  private void updateRecordAsync() {
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
    updateScorePercentageAsync();
  }

  private void updateHelpCallStateAsync() {
    getClientFactory().getRequestFactory().helpCallRequest().isCalling().fire(new Receiver<Boolean>() {

      @Override
      public void onSuccess(Boolean response) {
        showInformationMessage(getClientFactory().getMessages().fetchedHelpCallState());
        setCalling(response.booleanValue());
      }
    });
  }

  private void updateScorePercentageAsync() {
    getClientFactory().getRequestFactory().recordRequest().getMyRecord().fire(new Receiver<RecordProxy>() {

      @Override
      public void onSuccess(RecordProxy response) {
        ((StudentView)getTaskitView()).setRecord(response);
      }
    });
  }

  /**
   * TAの呼び出し状況を変更します。
   * <p>
   * このメソッドは表示のみを更新するだけであり、サーバーへの呼び出しのリクエストは送信しません。
   * 
   * @param calling 呼び出し状態にするのであればtrue,そうでなければfalse
   */
  void setCalling(boolean calling) {
    ((StudentView)getTaskitView()).setCalling(calling);
    if (calling) {
      updateHelpCallPositionAsync();
    }
  }

  /**
   * 自分が何番目の呼び出しなのかを表すポジションを非同期で更新しビューに反映します。
   */
  void updateHelpCallPositionAsync() {
    getClientFactory().getRequestFactory().helpCallRequest().getPosition().fire(new Receiver<Integer>() {

      @Override
      public void onSuccess(Integer response) {
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
