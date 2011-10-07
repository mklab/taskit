/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.LectureEditor;
import org.mklab.taskit.client.ui.ReportEditor;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.LectureProxy;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author yuhi
 */
public class AdminActivity extends TaskitActivity {

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

      @Override
      protected Widget initContent() {
        final LectureEditor lectureEditor = new LectureEditor(clientFactory.getMessages());
        clientFactory.getRequestFactory().lectureRequest().getAllLectures().fire(new Receiver<List<LectureProxy>>() {

          @Override
          public void onSuccess(List<LectureProxy> response) {
            lectureEditor.setLectures(response);
          }
        });
        return lectureEditor;
      }
    };
  }
}
