/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Student;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.shared.UserProxy;

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

  /** クライアントのクッキー中に保持する「自動更新するかどうか」のキーです。 */
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

  /**
   * {@inheritDoc}
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

  /**
   * クライアントのクッキーを調べ、自動ログインが有効であるかどうか調べます。
   * 
   * @return 自動ログインが有効であるかどうか
   */
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
        tryLoginAsync(view);
      }

    });
    return view;
  }

  /**
   * 自動ログインを非同期で試みます。
   * <p>
   * 自動ログインができない、すなわちサーバー上でセッションが残っていない場合には何も行いません。
   */
  void tryAutoLoginAsync() {
    this.clientFactory.getRequestFactory().userRequest().getLoginUser().fire(new Receiver<UserProxy>() {

      @Override
      public void onSuccess(UserProxy response) {
        if (response == null) return;
        goToTopPage(response);
      }

    });
  }

  /**
   * 非同期でログインを試みます。
   * <p>
   * ログインが成功した場合には直ちにユーザー別のトップページに移動します。失敗した場合はユーザーへのその通知のみを行います。
   * 
   * @param view ログインビュー。入力情報の取得、ステータス情報更新のために利用します。
   */
  void tryLoginAsync(final LoginView view) {
    final String id = view.getId();
    final String password = view.getPassword();
    this.clientFactory.getRequestFactory().accountRequest().login(id, password).fire(new Receiver<UserProxy>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(UserProxy response) {
        view.setStatusText(LoginActivity.this.clientFactory.getMessages().loginSuccessMessage());

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

  /**
   * ユーザー別のトップページ（ログイン後に表示される画面）に直ちに移動します。
   * 
   * @param user ユーザー情報
   */
  void goToTopPage(UserProxy user) {
    switch (user.getType()) {
      case TA:
      case TEACHER:
        this.clientFactory.getPlaceController().goTo(StudentList.INSTANCE);
        break;
      case STUDENT:
        this.clientFactory.getPlaceController().goTo(Student.INSTANCE);
    }
  }

}
