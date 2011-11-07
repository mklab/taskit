/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.CheckInListView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.CheckMapProxy;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * どのユーザーがどの学生を参照(担当)しているかどうかのリストを表示するアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public class CheckInListActivity extends TaskitActivity implements CheckInListView.Presenter {

  /**
   * {@link CheckInListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CheckInListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    updateCheckInListAsync();
  }

  private void updateCheckInListAsync() {
    getClientFactory().getRequestFactory().checkMapRequest().getAllCheckMap().with("student").fire(new Receiver<List<CheckMapProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<CheckMapProxy> response) {
            ((CheckInListView)getTaskitView()).setCheckInList(response);
          }

        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final CheckInListView checkInListView = clientFactory.getCheckInListView();
    checkInListView.setPresenter(this);
    return checkInListView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reloadButtonPressed() {
    updateCheckInListAsync();
  }

}
