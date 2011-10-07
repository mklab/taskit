/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.ReportEditor;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.user.client.ui.Widget;


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
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    return new AbstractTaskitView(clientFactory) {

      @Override
      protected Widget initContent() {
        return new ReportEditor();
      }
    };
  }

}
