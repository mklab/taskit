/**
 * 
 */
package org.mklab.taskit.client.activity;

import java.util.Calendar;
import java.util.Date;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentList;
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
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginActivity extends AbstractActivity {

  private static final String COOKIE_AUTO_LOGIN_KEY = "taskitAutoLogin"; //$NON-NLS-1$
  final LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);
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
    if (isAutoLoginEnabledClient()) {
      this.loginServiceAsync.isLoggedIn(new AsyncCallback<Boolean>() {

        @SuppressWarnings("unused")
        @Override
        public void onFailure(Throwable arg0) {
          // do nothing
        }

        @SuppressWarnings("unused")
        @Override
        public void onSuccess(Boolean arg0) {
          clientFactory.getPlaceController().goTo(StudentList.INSTANCE);
        }

      });
    }
    final LoginView view = createLoginView();

    panel.setWidget(view);
    view.requestFocus();
  }

  private boolean isAutoLoginEnabledClient() {
    String enabled = Cookies.getCookie(COOKIE_AUTO_LOGIN_KEY);
    if (enabled == null) return false;
    return enabled.toUpperCase().equals("TRUE"); //$NON-NLS-1$
  }

  private LoginView createLoginView() {
    final LoginView view = this.clientFactory.getLoginView();

    final LoginServiceAsync service = this.loginServiceAsync;
    view.getSubmitButton().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        service.login(view.getId(), view.getPassword(), new AsyncCallback<User>() {

          @Override
          public void onSuccess(User result) {
            TaskitActivity.LOGIN_USER = result;
            view.setStatusText("Successfully logged in.");
            clientFactory.getPlaceController().goTo(StudentList.INSTANCE);

            final boolean autoLoginEnabled = view.isAutoLoginEnabled();

            final int A_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
            final Date expire = new Date(System.currentTimeMillis() + 10 * A_DAY_IN_MILLIS);
            Cookies.setCookie(COOKIE_AUTO_LOGIN_KEY, String.valueOf(autoLoginEnabled), expire);
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
