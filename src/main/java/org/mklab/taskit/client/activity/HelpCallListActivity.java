/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.HelpCallProxy;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class HelpCallListActivity extends TaskitActivity implements HelpCallListView.Presenter {

  Timer timer;

  /**
   * {@link HelpCallListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public HelpCallListActivity(ClientFactory clientFactory) {
    super(clientFactory);
    this.timer = new Timer() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void run() {
        updateHelpCallList();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();

    updateHelpCallList();
    this.timer.scheduleRepeating(10 * 1000);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onStop() {
    this.timer.cancel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final HelpCallListView view = clientFactory.getHelpCallListView();
    view.setPresenter(this);

    return view;
  }

  private void updateHelpCallList() {
    getClientFactory().getRequestFactory().helpCallRequest().getAllHelpCalls().with("caller").fire(new Receiver<List<HelpCallProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<HelpCallProxy> response) {
            ((HelpCallListView)getTaskitView()).setHelpCalls(response);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void helpCallSelected(HelpCallProxy selectedHelpCall) {
    final AccountProxy caller = selectedHelpCall.getCaller();
    final Place to = new StudentList(caller.getId());
    getClientFactory().getPlaceController().goTo(to);
  }

}
