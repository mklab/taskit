/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.client.ui.admin.EntityEditorView;
import org.mklab.taskit.client.ui.admin.LectureEditorView;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * 講義データを編集する管理者向けのアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public final class LectureEditActivity extends TaskitActivity implements EntityEditorView.Presenter<LectureProxy> {

  private LectureRequest lectureRequest;
  private LectureEditorView lectureEditor;

  /**
   * {@link LectureEditActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LectureEditActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(final ClientFactory clientFactory) {
    this.lectureEditor = new LectureEditorView(clientFactory);
    this.lectureEditor.setPresenter(this);
    return this.lectureEditor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();
    updateLectureListDataAsync();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(final LectureProxy lecture) {
    final Messages messages = getClientFactory().getMessages();
    this.lectureRequest.updateOrCreate().using(lecture).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(messages.savedLectureMessage(lecture.getTitle()));
        updateLectureListDataAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateLectureListDataAsync();
        showErrorDialog(messages.savedLectureFailMessage(lecture.getTitle()) + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
    this.lectureRequest = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LectureProxy edit(LectureProxy lecture) {
    this.lectureRequest = getClientFactory().getRequestFactory().lectureRequest();
    if (lecture == null) return this.lectureRequest.create(LectureProxy.class);

    return this.lectureRequest.edit(lecture);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LectureProxy newEntity() {
    return edit(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final LectureProxy lecture) {
    final Messages messages = getClientFactory().getMessages();
    this.lectureRequest.delete().using(lecture).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(messages.deletedLectureMessage(lecture.getTitle()));
        updateLectureListDataAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        updateLectureListDataAsync();
        showErrorDialog(messages.deletedLectureFailMessage(lecture.getTitle()) + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
  }

  /**
   * 最新の講義データを非同期で取得しビューを更新します。
   */
  void updateLectureListDataAsync() {
    final LectureRequest req = getClientFactory().getRequestFactory().lectureRequest();
    final Messages messages = getClientFactory().getMessages();
    showInformationMessage(messages.fetchingLectureListMessage());
    req.getAllLectures().fire(new Receiver<List<LectureProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<LectureProxy> response) {
        showInformationMessage(messages.fetchedLectureListMessage());
        LectureEditActivity.this.lectureEditor.setEntities(response);
      }
    });
  }

}
