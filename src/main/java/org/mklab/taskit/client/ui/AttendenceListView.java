/**
 * 
 */
package org.mklab.taskit.client.ui;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public interface AttendenceListView extends TaskitView {

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter
   */
  void setPresenter(Presenter presenter);

  /**
   * 選択可能な全出席種別のオプションを設定します。
   * 
   * @param types 出席種別
   */
  void setAttendenceTypes(String[] types);

  /**
   * 出席一覧びゅーのプレゼンターを表すインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 30, 2011
   */
  public static interface Presenter {

    /**
     * 出席種別を設定します。
     * 
     * @param studentNo 学籍番号
     * @param type 出席種別
     */
    void setAttendenceType(String studentNo, String type);
  }

}
