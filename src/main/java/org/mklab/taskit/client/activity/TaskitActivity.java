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
import org.mklab.taskit.client.ui.event.ClickHandler;
import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


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

  private void setupLoginUserView(final HeaderView header) {
    setupLoginUserViewAsync(header);
  }

  private void setupLoginUserViewAsync(final HeaderView header) {
    getClientFactory().getRequestFactory().userRequest().getLoginUser().fire(new Receiver<UserProxy>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(UserProxy arg0) {
        if (arg0 == null) {
          logout();
          return;
        }
        setupLoginUserView(header, arg0);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
        return;
      }
    });
  }

  protected void logout() {
    try {
      getClientFactory().getRequestFactory().accountRequest().logout().fire();
    } finally {
      Cookies.removeCookie(LoginActivity.COOKIE_AUTO_LOGIN_KEY);
      getClientFactory().getPlaceController().goTo(Login.INSTANCE);
    }
  }

  private void setupLoginUserView(final HeaderView header, UserProxy loginUser) {
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
    showErrorMessage(e.toString());
  }

  protected final void showInformationMessage(String message) {
    Window.alert(message);
  }

}
