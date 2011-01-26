/**
 * 
 */
package org.mklab.taskit.client.ui;

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
  void setListData(String[] listData);

}
