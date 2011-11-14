/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.activity.TaskitActivity;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;


/**
 * ログイン後のすべてのアクティビティのライフサイクルを監視するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface ActivityObserver {

  /**
   * アクティビティの開始時に呼び出されます。
   * 
   * @param activity 開始されるアクティビティ
   */
  void onActivityStart(TaskitActivity activity);

  /**
   * アクティビティのビューの表示後に呼び出されます。
   * 
   * @param activity 表示されたアクティビティ
   * @param view 表示されたビュー
   * @param loginUser ログインユーザー
   */
  void onActivityViewShown(TaskitActivity activity, TaskitView view, UserProxy loginUser);

  /**
   * アクティビティ停止時に呼び出されます。
   * 
   * @param activity 停止されるアクティビティ
   */
  void onActivityStop(TaskitActivity activity);

}
