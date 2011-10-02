/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Student;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;

import java.util.Date;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * ログインページのアクティビティを表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public final class LoginActivity extends AbstractActivity {

  static final String COOKIE_AUTO_LOGIN_KEY = "taskitAutoLogin"; //$NON-NLS-1$
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
    }
    final LoginView view = createLoginView();

    panel.setWidget(view);
    view.requestFocus();
  }

  private static boolean isAutoLoginEnabledClient() {
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
    this.clientFactory.getRequestFactory().userRequest().getLoginUser().fire(new Receiver<UserProxy>() {

      @Override
      public void onSuccess(UserProxy response) {
        if (response == null) return;
        goToTopPage(response);
      }

    });
  }

  void tryLoginAsync(final LoginView view, final String id, final String password) {
    this.clientFactory.getRequestFactory().accountRequest().login(id, password).fire(new Receiver<UserProxy>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") UserProxy response) {
        view.setStatusText(getClientFactory().getMessages().loginSuccessMessage());

        final boolean autoLoginEnabled = view.isAutoLoginEnabled();
        storeAutoLoginState(autoLoginEnabled);

        goToTopPage(response);
      }

      private void storeAutoLoginState(final boolean autoLoginEnabled) {
        final int A_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
        final Date expire = new Date(System.currentTimeMillis() + 10 * A_DAY_IN_MILLIS);
        Cookies.setCookie(COOKIE_AUTO_LOGIN_KEY, String.valueOf(autoLoginEnabled), expire);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        view.setStatusText("Login failure. " + error.getMessage()); //$NON-NLS-1$
      }
    });
  }

  void goToTopPage(UserProxy user) {
    switch (user.getType()) {
      case TA:
      case TEACHER:
        getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
        break;
      case STUDENT:
        getClientFactory().getPlaceController().goTo(Student.INSTANCE);
    }
  }

}
