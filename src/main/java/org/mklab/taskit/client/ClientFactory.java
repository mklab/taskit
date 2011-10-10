/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.ProfileView;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.client.ui.admin.AdminView;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface ClientFactory {

  /**
   * RequestFactoryを取得します。
   * 
   * @return RequestFactory
   */
  TaskitRequestFactory getRequestFactory();

  /**
   * {@link EventBus}を取得します。
   * 
   * @return {@link EventBus}インスタンス
   */
  EventBus getEventBus();

  /**
   * {@link PlaceController}を取得します。
   * 
   * @return {@link PlaceController}インスタンス
   */
  PlaceController getPlaceController();

  /**
   * 文字列管理を行う{@link Messages}インスタンスを取得します。
   * 
   * @return {@link Messages}インスタンス
   */
  Messages getMessages();

  /**
   * ログインビューを取得します。
   * 
   * @return ログインビュー
   */
  LoginView getLoginView();

  /**
   * ページレイアウトを取得します。
   * 
   * @return ページレイアウト
   */
  PageLayout getPageLayout();

  /**
   * ヘッダビューを取得します。
   * 
   * @return ヘッダビュー
   */
  HeaderView getHeaderView();

  /**
   * 学生一覧ビューを取得します。
   * 
   * @return 学生一覧ビュー
   */
  StudentListView getStudentListView();

  /**
   * 学生用の成績閲覧ビューを取得します。
   * 
   * @return 成績閲覧ビュー
   */
  StudentView getStudentView();

  /**
   * 出席状況ビューを取得します。
   * 
   * @return 出席状況ビュー
   */
  AttendanceListView getAttendanceListView();

  /**
   * プロフィールビューを取得します。
   * 
   * @return プロフィールビュー
   */
  ProfileView getProfileView();

  /**
   * 呼び出し中の学生一覧表示ビューを取得します。
   * 
   * @return 呼び出し中の学生一覧表示ビューを取得します。
   */
  HelpCallListView getHelpCallListView();

  /**
   * 管理者ビューを取得します。
   * 
   * @return 管理者ビュー
   */
  AdminView getAdminView();

  /**
   * ヘルプコール監視オブジェクトを取得します。
   * 
   * @return ヘルプコール監視オブジェクト
   */
  HelpCallWatcher getHelpCallWatcher();
}
