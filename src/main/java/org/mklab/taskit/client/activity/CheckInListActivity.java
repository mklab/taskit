/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
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
    updateCheckInListAsync(false);
  }

  private void updateCheckInListAsync(boolean doReload) {
    final Receiver<List<CheckMapProxy>> receiver = new Receiver<List<CheckMapProxy>>() {

      @Override
      public void onSuccess(List<CheckMapProxy> response) {
        ((CheckInListView)getTaskitView()).setCheckInList(response);
      }
    };
    if (doReload) {
      getClientFactory().getLocalDatabase().getCacheAndExecute(LocalDatabase.CHECKS, receiver);
    } else {
      getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.CHECKS, receiver);
    }
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
  protected void onCheckMapChanged(List<CheckMapProxy> checks) {
    super.onCheckMapChanged(checks);
    updateCheckInListAsync(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reloadButtonPressed() {
    updateCheckInListAsync(true);
  }

}
