/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.AttendanceList;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.place.Profile;
import org.mklab.taskit.client.place.Student;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.ToolBarButton;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.model.UserType;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
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

  private static UserProxy loginUserCache;
  private ClientFactory clientFactory;
  private AcceptsOneWidget container;
  private HeaderView header;
  private UserProxy loginUser;
  private Place place;

  /**
   * {@link TaskitActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public TaskitActivity(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
    this.place = this.clientFactory.getPlaceController().getWhere();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
    /*
     * すぐにビューを表示せずに、ユーザー情報の取得が完了してから行います。
     */
    this.header = this.clientFactory.getHeaderView();
    this.container = panel;

    fetchLoginUserAndShowLater();
  }

  /**
   * ログインユーザーを取得します。
   * 
   * @return ログインユーザー
   */
  protected UserProxy getLoginUser() {
    return this.loginUser;
  }

  /**
   * ログインユーザーのキャッシュをクリアします。
   */
  protected static void clearLoginUserCache() {
    loginUserCache = null;
  }

  /**
   * ログインユーザー情報を取得し、取得が完了し次第ユーザーに応じたビューを表示します。
   * <p>
   * ユーザー情報はキャッシュされ、アプリケーション実行中はログアウトするまでその情報を利用します。<br>
   * アプリケーション実行中にユーザー種別やユーザー名が変わったりしても反映されないのでその時はブラウザをリロードしてください。
   * <p>
   * この仕様はプロフィール変更があることを考えると問題ありすぎなので、変更予定。
   */
  private void fetchLoginUserAndShowLater() {
    if (loginUserCache != null) {
      initViewWith(loginUserCache);
      return;
    }

    getClientFactory().getRequestFactory().userRequest().getLoginUser().with("account").fire(new Receiver<UserProxy>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(UserProxy user) {
            if (user == null) {
              logout();
              return;
            }
            loginUserCache = user;
            initViewWith(user);
          }

          /**
           * {@inheritDoc}
           */
          @Override
          public void onFailure(ServerFailure error) {
            showErrorMessage(error.getMessage());
            logout();
            return;
          }
        });
  }

  /**
   * 与えられたユーザー情報を元に、ビューを構築します。
   * 
   * @param user ログインユーザー情報
   */
  private void initViewWith(UserProxy user) {
    if (user == null) throw new NullPointerException();
    this.loginUser = user;

    initToolBarButtons(user);
    final String name = user.getName() == null ? user.getAccount().getId() : user.getName();
    this.header.setUserId(name);
    this.header.setUserType(user.getType().name());

    final DockLayoutPanel pn = new DockLayoutPanel(Unit.PX);
    pn.addNorth(this.header, this.header.getHeight());
    pn.add(createTaskitView(this.clientFactory));
    this.container.setWidget(pn);
  }

  /**
   * 上部のツールバーに表示するボタン、及びそのアクションを設定します。
   * 
   * @param user ログインユーザー情報
   */
  @SuppressWarnings({"nls", "unused"})
  private void initToolBarButtons(UserProxy user) {
    final UserType userType = user.getType();

    if (userType == UserType.TA || userType == UserType.TEACHER) {
      final ToolBarButton studentListButton = this.header.addButton("student_list");
      studentListButton.setIcon("taskit/students64.png");
      studentListButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
        }
      });

      final ToolBarButton attendanceListButton = this.header.addButton("attendance_list");
      attendanceListButton.setIcon("taskit/attendance64.png");
      attendanceListButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(AttendanceList.INSTANCE);
        }
      });
    }

    if (userType == UserType.TEACHER) {
      final ToolBarButton adminButton = this.header.addButton("admin");
      adminButton.setIcon("taskit/admin64.png");
      adminButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
        }
      });
    }

    if (userType == UserType.STUDENT) {
      final ToolBarButton studentButton = this.header.addButton("admin");
      studentButton.setIcon("taskit/student64.png");
      studentButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(Student.INSTANCE);
        }
      });
    }

    final ToolBarButton profileButton = this.header.addButton("profile");
    profileButton.setIcon("taskit/profile64.png");
    profileButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        getClientFactory().getPlaceController().goTo(Profile.INSTANCE);
      }
    });

    this.header.addSeparator();

    final ToolBarButton logoutButton = this.header.addButton("logout");
    logoutButton.setIcon("taskit/logout64.png");
    logoutButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        logout();
      }
    });
  }

  /**
   * ビューを作成します。
   * 
   * @param clientFactory クライアントファクトリ
   * @return ビュー
   */
  protected abstract Widget createTaskitView(@SuppressWarnings("hiding") ClientFactory clientFactory);

  /**
   * ログアウトします。
   */
  protected void logout() {
    loginUserCache = null;
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

  /**
   * ページへ再遷移します。
   */
  protected final void reload() {
    getClientFactory().getPlaceController().goTo(this.place);
  }

}
