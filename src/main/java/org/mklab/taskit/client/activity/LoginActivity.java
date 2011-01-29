/**
 * 
 */
package org.mklab.taskit.client.activity;

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
 * ログインページのアクティビティを表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public final class LoginActivity extends AbstractActivity {

  static final String COOKIE_AUTO_LOGIN_KEY = "taskitAutoLogin"; //$NON-NLS-1$
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

  ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget,
   *      com.google.gwt.event.shared.EventBus)
   */
  @Override
  public void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
    if (isAutoLoginEnabledClient()) {
      tryAutoLoginAsync();
      return;
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

    view.getSubmitButton().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        final String id = view.getId();
        final String password = view.getPassword();
        tryLoginAsync(view, id, password);
      }

    });
    return view;
  }

  void tryAutoLoginAsync() {
    this.loginServiceAsync.getLoginUser(new AsyncCallback<User>() {

      @SuppressWarnings("unused")
      @Override
      public void onFailure(Throwable caught) {
        // do nothing
      }

      @SuppressWarnings("unused")
      @Override
      public void onSuccess(final User result) {
        goToTopPage();
      }

    });
  }

  void tryLoginAsync(final LoginView view, final String id, final String password) {
    this.loginServiceAsync.login(id, password, new AsyncCallback<User>() {

      @SuppressWarnings("unused")
      @Override
      public void onSuccess(User result) {
        view.setStatusText(getClientFactory().getMessages().loginSuccessMessage());

        final boolean autoLoginEnabled = view.isAutoLoginEnabled();
        storeAutoLoginState(autoLoginEnabled);

        goToTopPage();
      }

      private void storeAutoLoginState(final boolean autoLoginEnabled) {
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

  void goToTopPage() {
    getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
  }

}
