/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.EntityEditorView;
import org.mklab.taskit.client.ui.ReportEditorView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.ReportRequest;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author yuhi
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
    updateReportListData();

    getClientFactory().getRequestFactory().lectureRequest().getAllLectures().with().fire(new Receiver<List<LectureProxy>>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onSuccess(List<LectureProxy> response) {
        lectures = response;
        reportEditor.setChoosableLectures(response);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(ReportProxy report) {
    this.reportRequest.updateOrCreate().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        updateReportListData();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateReportListData();
        showErrorMessage(error.getMessage());
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
      showErrorMessage("Lecture data not fetched. Please retry later."); //$NON-NLS-1$
      return null;
    }
    if (this.lectures.size() == 0) {
      showErrorMessage("No lecture data exists. Please create at least one lecture before do this."); //$NON-NLS-1$
      return null;
    }

    final ReportProxy report = edit(null);
    report.setLecture(this.lectures.get(0));
    return report;
  }

  void updateReportListData() {
    final ReportRequest req = getClientFactory().getRequestFactory().reportRequest();
    req.getAllReports().with("lecture").fire(new Receiver<List<ReportProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<ReportProxy> response) {
            ReportEditActivity.this.reportEditor.setEntities(response);
          }
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(ReportProxy report) {
    this.reportRequest.delete().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        updateReportListData();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateReportListData();
        showErrorMessage(error.getMessage());
      }
    });
  }

}
