/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.LectureEditor;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author yuhi
 */
public class AdminActivity extends TaskitActivity implements LectureEditor.Presenter {

  private LectureRequest lectureRequest;
  private LectureEditor lectureEditor;

  /**
   * {@link AdminActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(final ClientFactory clientFactory) {
    return new AbstractTaskitView(clientFactory) {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      protected Widget initContent() {
        lectureEditor = new LectureEditor(clientFactory.getMessages());
        lectureEditor.setPresenter(AdminActivity.this);
        updateLectureListData();
        return lectureEditor;
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(LectureProxy lecture) {
    this.lectureRequest.updateOrCreate().using(lecture).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
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
  public LectureProxy newLecture() {
    return edit(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateLectureListData() {
    final LectureRequest req = getClientFactory().getRequestFactory().lectureRequest();
    req.getAllLectures().fire(new Receiver<List<LectureProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<LectureProxy> response) {
        final List<LectureProxy> editableList = new ArrayList<LectureProxy>();
        for (LectureProxy lecture : response) {
          editableList.add(lecture);
        }
        AdminActivity.this.lectureEditor.setLectures(editableList);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(LectureProxy lecture) {
    this.lectureRequest.delete().using(lecture).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
      }
    });
  }
}
