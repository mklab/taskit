/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ActivityObserver;
import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.event.RemoteEventListenerDecorator;
import org.mklab.taskit.client.event.StudentRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TaRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TeacherRemoteEventListenerDecorator;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.ui.HelpCallDisplayable;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.event.CheckMapEvent;
import org.mklab.taskit.shared.event.HelpCallEvent;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * 認証完了後のすべてのアクティビティの基底クラスです。
 * <p>
 * ビューの基本的なレイアウト、ユーザーへの情報通知機能を提供します。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public abstract class TaskitActivity extends AbstractActivity implements PageLayout.Presenter {

  private ClientFactory clientFactory;
  private AcceptsOneWidget container;
  private UserProxy loginUser;
  private TaskitView view;
  private PageLayout layout;
  private ActivityObserver observer;

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

  final void setActivityObserver(ActivityObserver observer) {
    this.observer = observer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void start(AcceptsOneWidget panel, @SuppressWarnings("unused") EventBus eventBus) {
    this.observer.onActivityStart(this);
    this.container = panel;
    updateLoginUserInfoAsync();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void onStop() {
    super.onStop();
    this.observer.onActivityStop(this);
    handleOnStop();
  }

  /**
   * アクティビティ停止時に呼び出されます。
   * 
   * @see #onStop()
   */
  protected void handleOnStop() {
    // do nothing
  }

  /**
   * ログインユーザーを取得します。
   * 
   * @return ログインユーザー
   */
  protected final UserProxy getLoginUser() {
    return this.loginUser;
  }

  /**
   * ログインユーザー情報を非同期で取得し、取得が完了し次第ユーザーに応じたビューを表示します。
   * <p>
   * ユーザー情報はキャッシュされ、アプリケーション実行中はログアウトするか、キャッシュをマニュアルでクリアするまでその情報を利用します。
   */
  private void updateLoginUserInfoAsync() {
    getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.LOGIN_USER, new Receiver<UserProxy>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(UserProxy user) {
        if (user == null) {
          logout();
          return;
        }
        initViewWith(user);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorDialog(error.getMessage());
        logout();
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
    this.observer.onActivityViewShown(this, this.view, user);

    observeRemoteEvent(user);
    onViewShown();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void observeRemoteEvent(UserProxy user) {
    final RemoteEventListenerDecorator<?> listener;
    switch (user.getType()) {
      case STUDENT:
        listener = createRemoteEventListenerForStudent();
        break;
      case TA:
        listener = createRemoteEventListenerForTa();
        break;
      case TEACHER:
        listener = createRemoteEventListenerForTeacher();
        break;
      default:
        throw new UnsupportedOperationException();
    }

    final RemoteEventListenerDecorator globalListener = getClientFactory().getGlobalEventListener().getGlobalListener();
    globalListener.setListener(listener);
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
   * <p>
   * このメソッドは、認証、ユーザー情報の取得が完了し、ビューの構築が完了した後で呼び出されます。 <br>
   * オーバーライドして利用するように設計されています。
   */
  protected void onViewShown() {
    // do nothing
  }

  /**
   * アクティビティで表示するビューを取得します。
   * 
   * @return ビュー。まだ生成されていない場合はnull
   */
  protected final TaskitView getTaskitView() {
    return this.view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void logout() {
    if (getClientFactory().getGlobalEventListener().isListening()) {
      getClientFactory().getGlobalEventListener().unlisten();
    }
    /*
     * ログアウトを先にしてしまうと、Activity#onStop()での処理がログアウト後になってしまい行えないため。
     * アクティビティの移動後ログアウトを行う。
     * 
     * 出来たらログアウト完了後移動にしたい。
     */
    getClientFactory().getPlaceController().goTo(Login.INSTANCE);
    try {
      getClientFactory().getRequestFactory().accountRequest().logout().fire();
    } catch (Throwable e) {
      showErrorDialog(e);
    } finally {
      Cookies.removeCookie(LoginActivity.COOKIE_AUTO_LOGIN_KEY);
    }
    this.clientFactory.initialize();
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
   * 先生用のリモートイベントリスナを生成します。
   * 
   * @return 先生用のリモートイベントリスナ
   */
  protected TeacherRemoteEventListenerDecorator createRemoteEventListenerForTeacher() {
    final TaRemoteEventListenerDecorator l = createRemoteEventListenerForTa();
    return new TeacherRemoteEventListenerDecorator() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void helpCallChanged(HelpCallEvent evt) {
        super.helpCallChanged(evt);
        l.helpCallChanged(evt);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void checkMapChanged(CheckMapEvent evt) {
        super.checkMapChanged(evt);
        l.checkMapChanged(evt);
      }
    };
  }

  /**
   * TA用のリモートイベントリスナを生成します。
   * 
   * @return TA用のリモートイベントリスナ
   */
  protected TaRemoteEventListenerDecorator createRemoteEventListenerForTa() {
    return new TaRemoteEventListenerDecorator() {

      /**
       * {@inheritDoc}
       */
      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void helpCallChanged(HelpCallEvent evt) {
        super.helpCallChanged(evt);
        if (view instanceof HelpCallDisplayable) {
          ((HelpCallDisplayable)view).setHelpCallDisplayEnabled(true);
          ((HelpCallDisplayable)view).showHelpCallCount(evt.getHelpCallCount());
        }
      }
    };
  }

  /**
   * 生徒用のリモートイベントリスナを生成します。
   * 
   * @return 生徒用のリモートイベントリスナ
   */
  protected StudentRemoteEventListenerDecorator createRemoteEventListenerForStudent() {
    return new StudentRemoteEventListenerDecorator();
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

}
