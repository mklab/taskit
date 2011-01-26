/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.Login;
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
  protected static User LOGIN_USER = null;
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

  private void setupHeader(final TaskitView taskitView) {
    final HeaderView header = taskitView.getHeader();
    if (LOGIN_USER != null) {
      header.setUserId(LOGIN_USER.getId());
      header.setUserType(LOGIN_USER.getType().name());
    }

    header.getAdminLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
      }
    });
    header.getLogoutTrigger().addClickHandler(new ClickHandler() {

      @SuppressWarnings("unused")
      @Override
      public void onClick(ClickEvent event) {
        final LoginServiceAsync service = GWT.create(LoginService.class);
        service.logout(new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            logout();
          }

          @Override
          public void onFailure(Throwable caught) {
            logout();
          }

          private void logout() {
            Cookies.removeCookie(LoginActivity.COOKIE_AUTO_LOGIN_KEY);
            getClientFactory().getPlaceController().goTo(Login.INSTANCE);
          }
        });
      }
    });
  }

  protected final ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * ビューを作成します。
   * 
   * @param clientFactory クライアントファクトリ
   * @return ビュー
   */
  protected abstract TaskitView createTaskitView(@SuppressWarnings("hiding") ClientFactory clientFactory);

  protected final void showErrorMessage(String errorMessage) {
    Window.alert(errorMessage);
  }

  protected final void showInformationMessage(String message) {
    Window.alert(message);
  }

}
