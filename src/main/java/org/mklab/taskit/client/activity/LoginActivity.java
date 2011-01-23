/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.DashboardPlace;
import org.mklab.taskit.client.place.LoginPlace;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.model.User;
import org.mklab.taskit.service.LoginFailureException;
import org.mklab.taskit.service.LoginService;
import org.mklab.taskit.service.LoginServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginActivity extends AbstractActivity {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place PLACE = new LoginPlace("login"); //$NON-NLS-1$

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
    final LoginView view = createLoginView();

    panel.setWidget(view);
    view.requestFocus();
  }

  private LoginView createLoginView() {
    final LoginView view = this.clientFactory.getLoginView();
    final LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);

    view.getSubmitButton().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        loginServiceAsync.login(view.getId(), view.getPassword(), new AsyncCallback<User>() {

          @Override
          public void onSuccess(User result) {
            view.setStatusText("Successfully logged in.");
            clientFactory.getPlaceController().goTo(DashboardActivity.PLACE);
          }

          @Override
          public void onFailure(Throwable caught) {
            if (caught instanceof LoginFailureException) {
              view.setStatusText(String.valueOf(((LoginFailureException)caught).getErrorCode()));
              return;
            }

            view.setStatusText("Invalid state." + caught.toString()); //$NON-NLS-1$
          }
        });
      }
    });
    return view;
  }

}