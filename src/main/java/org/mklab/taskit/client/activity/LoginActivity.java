/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.LoginView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginActivity extends AbstractActivity {

  private ClientFactory clientFactory;

  /**
   * {@link LoginActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LoginActivity(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
  }

  /**
   * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget,
   *      com.google.gwt.event.shared.EventBus)
   */
  @Override
  public void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
    final LoginView view = this.clientFactory.getLoginView();
    panel.setWidget(view);
    view.requestFocus();
  }

}
