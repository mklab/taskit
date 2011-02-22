/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.StudentwiseEvaluationView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface ClientFactory {

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
   * 学生成績ビューを取得します。
   * 
   * @return 成績ビュー
   */
  StudentwiseEvaluationView getStudentEvaluationView();

}
