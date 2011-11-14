/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.event.GlobalEventListener;
import org.mklab.taskit.client.event.RemoteEventListenerDecorator;
import org.mklab.taskit.client.event.StudentRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TaRemoteEventListenerDecorator;
import org.mklab.taskit.client.event.TeacherRemoteEventListenerDecorator;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.ui.HelpCallDisplayable;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.shared.event.CheckMapEvent;
import org.mklab.taskit.shared.event.HelpCallEvent;

import java.util.List;

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
    updateLoginUserInfoAsync();
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

    // ログイン後、初めて表示されるアクティビティの場合にのみ、リモートイベントの監視を開始する。
    final GlobalEventListener globalEventListener = this.clientFactory.getGlobalEventListener();
    if (globalEventListener.isListening() == false) {
      globalEventListener.listenWith(user);
    }

    this.view = createTaskitView(this.clientFactory);
    updateHelpCallDisplayAsync();

    final Widget page = this.layout.layout(this.view, user);
    this.container.setWidget(page);

    observeRemoteEvent(user);
    onViewShown();
  }

  /**
   * 現在のヘルプコール数を取得し、ビューに表示します。
   * <p>
   * {@link LocalDatabase}を使用しており、過去に取得していれば新たに取得は行わずに過去のデータを利用します。
   */
  private void updateHelpCallDisplayAsync() {
    if (this.view instanceof HelpCallDisplayable) {
      final HelpCallDisplayable helpCallDisplay = (HelpCallDisplayable)this.view;
      final boolean isDisplayable = getLoginUser().getType() != UserType.STUDENT;
      helpCallDisplay.setHelpCallDisplayEnabled(isDisplayable);
      if (isDisplayable) {
        getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.CALL_LIST, new Receiver<List<HelpCallProxy>>() {

          @Override
          public void onSuccess(List<HelpCallProxy> response) {
            helpCallDisplay.showHelpCallCount(response.size());
          }
        });
      }
    }
  }

  /**
   * ユーザー種別を元に適切なEventListenerを生成し、設定します。
   * 
   * @param user ログインユーザー
   */
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
    return new TeacherRemoteEventListenerDecorator(null) {

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
    return new TaRemoteEventListenerDecorator(null) {

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
        getClientFactory().getLocalDatabase().execute(LocalDatabase.CALL_LIST);
      }
    };
  }

  /**
   * 生徒用のリモートイベントリスナを生成します。
   * 
   * @return 生徒用のリモートイベントリスナ
   */
  protected StudentRemoteEventListenerDecorator createRemoteEventListenerForStudent() {
    return new StudentRemoteEventListenerDecorator(null);
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
