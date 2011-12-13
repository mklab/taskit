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
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.client.ui.admin.EntityEditorView;
import org.mklab.taskit.client.ui.admin.ReportEditorView;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.ReportRequest;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * 課題データの編集を行うアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public class ReportEditActivity extends TaskitActivity implements EntityEditorView.Presenter<ReportProxy> {

  private ReportRequest reportRequest;
  private ReportEditorView reportEditor;
  private List<LectureProxy> lectures;

  /**
   * {@link ReportEditActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public ReportEditActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(final ClientFactory clientFactory) {
    this.reportEditor = new ReportEditorView(clientFactory);
    this.reportEditor.setPresenter(this);
    return this.reportEditor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();
    updateReportListDataAsync();
    updateLecturesAsync();
  }

  /**
   * 講義データを非同期で取得し、ビューを更新します。
   */
  private void updateLecturesAsync() {
    final Messages messages = getClientFactory().getMessages();
    showInformationMessage(messages.fetchingChoosableLecturesMessage());
    getClientFactory().getRequestFactory().lectureRequest().getAllLectures().with().fire(new Receiver<List<LectureProxy>>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onSuccess(List<LectureProxy> response) {
        showInformationMessage(messages.fetchedChoosableLecturesMessage());
        lectures = response;
        reportEditor.setChoosableLectures(response);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(final ReportProxy report) {
    final Messages messages = getClientFactory().getMessages();
    this.reportRequest.updateOrCreate().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(messages.savedReportMessage(report.getTitle()));
        updateReportListDataAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateReportListDataAsync();
        showErrorDialog(messages.savedReportFailMessage(report.getTitle()) + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
    this.reportRequest = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ReportProxy edit(ReportProxy lecture) {
    this.reportRequest = getClientFactory().getRequestFactory().reportRequest();
    if (lecture == null) return this.reportRequest.create(ReportProxy.class);

    return this.reportRequest.edit(lecture);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ReportProxy newEntity() {
    if (this.lectures == null) {
      showErrorDialog("Lecture data not fetched. Please retry later."); //$NON-NLS-1$
      return null;
    }
    if (this.lectures.size() == 0) {
      showErrorDialog("No lecture data exists. Please create at least one lecture before do this."); //$NON-NLS-1$
      return null;
    }

    final ReportProxy report = edit(null);
    report.setLecture(this.lectures.get(0));
    return report;
  }

  /**
   * 課題データを非同期で取得しビューを更新します。
   */
  void updateReportListDataAsync() {
    final ReportRequest req = getClientFactory().getRequestFactory().reportRequest();
    final Messages messages = getClientFactory().getMessages();
    showInformationMessage(messages.fetchingReportListMessage());
    req.getAllReports().with("lecture").fire(new Receiver<List<ReportProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<ReportProxy> response) {
            showInformationMessage(messages.fetchedReportListMessage());
            ReportEditActivity.this.reportEditor.setEntities(response);
          }
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final ReportProxy report) {
    final Messages messages = getClientFactory().getMessages();
    this.reportRequest.delete().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(messages.deletedReportMessage(report.getTitle()));
        updateReportListDataAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateReportListDataAsync();
        showErrorMessage(messages.deletedReportFailMessage(report.getTitle()) + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
  }

}
