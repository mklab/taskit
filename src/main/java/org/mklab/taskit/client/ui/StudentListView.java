/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.UserProxy;

import java.util.List;


/**
 * 学生一覧ビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public interface StudentListView extends TaskitView {

  /**
   * リストデータを設定します。
   * 
   * @param listData リストデータ
   */
  void setListData(List<UserProxy> listData);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * このビューのプレゼンターを表すインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, 2011/01/26
   */
  public static interface Presenter {

    /**
     * リストデータがクリックされたときに呼び出されます。
     * 
     * @param index クリックされた行インデックス
     */
    void listDataClicked(int index);
  }

}
