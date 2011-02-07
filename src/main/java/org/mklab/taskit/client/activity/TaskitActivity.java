/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.AttendanceList;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.service.LoginService;
import org.mklab.taskit.shared.service.LoginServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import org.mklab.taskit.client.ui.event.ClickHandler;
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

    header.addAdminLinkClickHandler(new ClickHandler() {

      @Override
      public void onClick() {
        getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
      }

    });
    header.addStudentListLinkClickHandler(new ClickHandler() {

      @Override
      public void onClick() {
        getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
      }
    });
    header.addAttendanceListLinkClickHandler(new ClickHandler() {

      @Override
      public void onClick() {
        getClientFactory().getPlaceController().goTo(AttendanceList.INSTANCE);
      }
    });
    header.addLogoutLinkClickHandler(new ClickHandler() {

      @Override
      public void onClick() {
        logout();
      }
    });
  }

  protected void logout() {
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
    setupLoginUserViewAsync(header);
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
        setupLoginUserView(header, arg0);
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

  protected final void showErrorMessage(Throwable e) {
    final Throwable cause = e.getCause();
    if (cause != null) {
      showErrorMessage(cause.getMessage());
      return;
    }

    showErrorMessage(e.toString());
  }

  protected final void showInformationMessage(String message) {
    Window.alert(message);
  }

}
