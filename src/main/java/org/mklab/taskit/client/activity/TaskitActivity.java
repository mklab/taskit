/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.AttendenceList;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.service.LoginService;
import org.mklab.taskit.shared.service.LoginServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public abstract class TaskitActivity extends AbstractActivity {

  /*
   * FIXME ログイン時に設定される値ですが、デザイン的にひどい。
   * うまくユーザーインスタンスを管理することができれば切り替えたいです。
   * この値はこのクラス以外からは利用しないでください。
   */
  private static User LOGIN_USER_CACHE = null;
  private ClientFactory clientFactory;

  /**
   * {@link TaskitActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public TaskitActivity(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
  }

  /**
   * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget,
   *      com.google.gwt.event.shared.EventBus)
   */
  @Override
  public final void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
    final TaskitView taskitView = createTaskitView(this.clientFactory);
    setupHeader(taskitView);
    panel.setWidget(taskitView);
  }

  /**
   * ビューを作成します。
   * 
   * @param clientFactory クライアントファクトリ
   * @return ビュー
   */
  protected abstract TaskitView createTaskitView(@SuppressWarnings("hiding") ClientFactory clientFactory);

  private void setupHeader(final TaskitView taskitView) {
    final HeaderView header = taskitView.getHeader();
    setupLoginUserView(header);

    header.getAdminLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
      }
    });
    header.getStudentListLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
      }
    });
    header.getAttendenceListLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(AttendenceList.INSTANCE);
      }
    });
    header.getLogoutTrigger().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        logout();
      }

    });
  }

  void logout() {
    final LoginServiceAsync service = GWT.create(LoginService.class);
    service.logout(new AsyncCallback<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void result) {
        logout();
      }

      @Override
      public void onFailure(@SuppressWarnings("unused") Throwable caught) {
        logout();
      }

      private void logout() {
        Cookies.removeCookie(LoginActivity.COOKIE_AUTO_LOGIN_KEY);
        getClientFactory().getPlaceController().goTo(Login.INSTANCE);
      }
    });
  }

  private void setupLoginUserView(final HeaderView header) {
    if (LOGIN_USER_CACHE == null) {
      setupLoginUserViewAsync(header);
      return;
    }

    setupLoginUserView(header, LOGIN_USER_CACHE);
  }

  private void setupLoginUserViewAsync(final HeaderView header) {
    final LoginServiceAsync service = GWT.create(LoginService.class);
    service.getLoginUser(new AsyncCallback<User>() {

      /**
       * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
       */
      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(User arg0) {
        if (arg0 == null) {
          logout();
          return;
        }
        LOGIN_USER_CACHE = arg0;
        setupLoginUserView(header, LOGIN_USER_CACHE);
      }

      /**
       * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
       */
      @Override
      public void onFailure(Throwable arg0) {
        showErrorMessage(arg0.toString());
        return;
      }
    });
  }

  private void setupLoginUserView(final HeaderView header, User loginUser) {
    header.setUserId(loginUser.getId());
    header.setUserType(loginUser.getType().name());
  }

  protected final ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  protected final void showErrorMessage(String errorMessage) {
    Window.alert(errorMessage);
  }

  protected final void showInformationMessage(String message) {
    Window.alert(message);
  }

}
