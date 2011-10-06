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
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class HelpCallListActivity extends TaskitActivity implements HelpCallListView.Presenter {

  /**
   * {@link HelpCallListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public HelpCallListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final HelpCallListView view = clientFactory.getHelpCallListView();
    view.setPresenter(this);

    clientFactory.getRequestFactory().helpCallRequest().getAllHelpCalls().with("caller").fire(new Receiver<List<HelpCallProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<HelpCallProxy> response) {
            view.setHelpCalls(response);
          }

          /**
           * {@inheritDoc}
           */
          @Override
          public void onFailure(ServerFailure error) {
            showErrorMessage(error.getMessage());
          }
        });
    return view;
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
