/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentListPlace;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.service.LoginFailureException;
import org.mklab.taskit.shared.service.LoginService;
import org.mklab.taskit.shared.service.LoginServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
            Window.alert("Hello " + result.getId() + ". You are a " + result.getType());
            clientFactory.getPlaceController().goTo(StudentListPlace.INSTANCE);
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
