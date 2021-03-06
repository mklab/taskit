/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.event.GlobalEventListener;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.CheckInListView;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.ProfileView;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.client.ui.admin.AdminView;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;


/**
 * クライアントの端末に応じたビューや、すべてのビューで共通して利用するオブジェクトを提供するインターフェースです。
 * <p>
 * この実装を切り替えることにより、デスクトップPC用、タブレットPC用、スマートフォン用などでビューを切り替えることが出来ます。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface ClientFactory {

  /**
   * オブジェクト生成時の状態に初期化します。
   */
  void initialize();

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
   * ローカルデータベースを取得します。
   * 
   * @return ローカルデータベース
   */
  LocalDatabase getLocalDatabase();

  /**
   * アプリケーション全体で利用するイベントを監視するリスナーを取得します。
   * 
   * @return アプリケーション全体で利用するイベントを監視するリスナー
   */
  GlobalEventListener getGlobalEventListener();

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
   * チェックインリストビューを取得します。
   * 
   * @return チェックインリスト
   */
  CheckInListView getCheckInListView();
}
