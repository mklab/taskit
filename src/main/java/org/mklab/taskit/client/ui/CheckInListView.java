/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.CheckMapProxy;

import java.util.List;


/**
 * 誰がどの生徒を担当しているかどうかの一覧を表示するビューです。
 * 
 * @author Yuhi Ishikura
 */
public interface CheckInListView extends TaskitView {

  /**
   * チェックリストを設定します。
   * 
   * @param checks チェックリスト
   */
  void setCheckInList(List<CheckMapProxy> checks);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link CheckInListView}のプレゼンターです。
   * 
   * @author Yuhi Ishikura
   */
  public static interface Presenter {

    /**
     * 更新ボタンが押されたときに呼び出されます。
     */
    void reloadButtonPressed();
  }

}
