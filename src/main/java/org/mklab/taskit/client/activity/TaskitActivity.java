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
import org.mklab.taskit.client.ui.ToolBarButton;
import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
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
    HeaderView header = this.clientFactory.getHeaderView();
    setupHeader(header);
    final DockLayoutPanel pn = new DockLayoutPanel(Unit.PX);
    pn.addNorth(header, header.getHeight());
    pn.add(createTaskitView(this.clientFactory));
    panel.setWidget(pn);
  }

  /**
   * ビューを作成します。
   * 
   * @param clientFactory クライアントファクトリ
   * @return ビュー
   */
  protected abstract Widget createTaskitView(@SuppressWarnings("hiding") ClientFactory clientFactory);

  @SuppressWarnings({"nls", "unused"})
  private void setupHeader(final HeaderView header) {
    updateLoginUserViewAsync();

    final ToolBarButton studentListButton = header.addButton("student_list");
    studentListButton.setIcon("taskit/students64.png");
    studentListButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
      }
    });

    final ToolBarButton attendanceListButton = header.addButton("attendance_list");
    attendanceListButton.setIcon("taskit/attendance64.png");
    attendanceListButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        getClientFactory().getPlaceController().goTo(AttendanceList.INSTANCE);
      }
    });

    final ToolBarButton adminButton = header.addButton("admin");
    adminButton.setIcon("taskit/admin64.png");
    adminButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
      }
    });

    header.addSeparator();

    final ToolBarButton logoutButton = header.addButton("logout");
    logoutButton.setIcon("taskit/logout64.png");
    logoutButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        logout();
      }
    });
  }

  private void updateLoginUserViewAsync() {
    final HeaderView header = getClientFactory().getHeaderView();
    getClientFactory().getRequestFactory().userRequest().getLoginUser().with("account").fire(new Receiver<UserProxy>() { //$NON-NLS-1$

          @Override
          public void onSuccess(UserProxy arg0) {
            if (arg0 == null) {
              logout();
              return;
            }
            header.setUserId(arg0.getAccount().getId());
            header.setUserType(arg0.getType().name());
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

  /**
   * ログアウトします。
   */
  protected void logout() {
    try {
      getClientFactory().getRequestFactory().accountRequest().logout().fire();
    } finally {
      Cookies.removeCookie(LoginActivity.COOKIE_AUTO_LOGIN_KEY);
      getClientFactory().getPlaceController().goTo(Login.INSTANCE);
    }
  }

  /**
   * {@link ClientFactory}を取得します。
   * 
   * @return クライアントファクトリ
   */
  protected final ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * エラーメッセージを表示します。
   * 
   * @param errorMessage エラーメッセージ
   */
  @SuppressWarnings("static-method")
  protected final void showErrorMessage(String errorMessage) {
    Window.alert(errorMessage);
  }

  /**
   * エラーメッセージを表示します。
   * 
   * @param e 例外
   */
  protected final void showErrorMessage(Throwable e) {
    showErrorMessage(e.toString());
  }

  /**
   * 情報メッセージを表示します。
   * 
   * @param message メッセージ
   */
  @SuppressWarnings("static-method")
  protected final void showInformationMessage(String message) {
    Window.alert(message);
  }

}
