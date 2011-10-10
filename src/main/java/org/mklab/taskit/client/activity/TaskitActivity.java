/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.HelpCallWatcher;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.ui.HelpCallDisplayable;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public abstract class TaskitActivity extends AbstractActivity implements PageLayout.Presenter, HelpCallObserver {

  private static UserProxy loginUserCache;
  private ClientFactory clientFactory;
  private AcceptsOneWidget container;
  private UserProxy loginUser;
  private TaskitView view;
  private PageLayout layout;

  /**
   * {@link TaskitActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public TaskitActivity(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
    this.layout = clientFactory.getPageLayout();
    this.layout.setPresenter(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
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

    final Request<UserProxy> request = getClientFactory().getRequestFactory().userRequest().getLoginUser();
    request.with("account"); //$NON-NLS-1$
    request.fire(new Receiver<UserProxy>() {

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
        showErrorDialog(error.getMessage());
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

    this.view = createTaskitView(this.clientFactory);
    Widget page = this.layout.layout(this.view, user);
    this.container.setWidget(page);

    final HelpCallWatcher helpCallWatcher = this.clientFactory.getHelpCallWatcher();
    if (user.getType() == UserType.STUDENT) {
      helpCallWatcher.stop();
    } else {
      helpCallWatcher.setHelpCallObserver(this);
      helpCallWatcher.start();
      if (this.view instanceof HelpCallDisplayable) {
        ((HelpCallDisplayable)this.view).showHelpCallCount(helpCallWatcher.getHelpCallCount());
      }
    }

    onViewShown();
  }

  /**
   * ビューを作成します。
   * 
   * @param clientFactory クライアントファクトリ
   * @return ビュー
   */
  protected abstract TaskitView createTaskitView(@SuppressWarnings("hiding") ClientFactory clientFactory);

  /**
   * ビューが表示されたときに呼び出されます。
   */
  protected void onViewShown() {
    // do nothing
  }

  /**
   * アクティビティで表示するビューを取得します。
   * 
   * @return ビュー。まだ生成されていない場合はnull
   */
  protected TaskitView getTaskitView() {
    return this.view;
  }

  /**
   * @{inheritDoc
   */
  @Override
  public void logout() {
    loginUserCache = null;
    this.clientFactory.getHelpCallWatcher().stop();
    try {
      getClientFactory().getRequestFactory().accountRequest().logout().fire();
    } catch (Throwable e) {
      showErrorDialog(e);
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
  protected final void showErrorDialog(String errorMessage) {
    if (this.view == null) {
      Window.alert(errorMessage);
    } else {
      this.view.showErrorDialog(errorMessage);
    }
  }

  /**
   * エラーメッセージを表示します。
   * 
   * @param e 例外
   */
  protected final void showErrorDialog(Throwable e) {
    showErrorDialog(e.toString());
  }

  /**
   * 情報メッセージを表示します。
   * 
   * @param message メッセージ
   */
  protected final void showInformationDialog(String message) {
    if (this.view == null) {
      Window.alert(message);
    } else {
      this.view.showInformationDialog(message);
    }
  }

  /**
   * エラーメッセージを表示します。
   * 
   * @param message エラーメッセージ
   */
  protected final void showErrorMessage(String message) {
    if (this.view == null) showErrorDialog(message);
    this.view.showErrorMessage(message);
  }

  /**
   * 情報メッセージを表示します。
   * 
   * @param message 情報メッセージ
   */
  protected final void showInformationMessage(String message) {
    if (this.view == null) showInformationDialog(message);
    this.view.showInformationMessage(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void helpCallCountChanged(int count) {
    if (this.view == null) return;
    if (this.view instanceof HelpCallDisplayable) {
      ((HelpCallDisplayable)this.view).showHelpCallCount(count);
    }
  }

}
