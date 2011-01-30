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
   * 与えられたインデックスの学生番号を設定します。
   * 
   * @param index インデックス
   * @param studentNo 学籍番号
   */
  void setStudentNumber(int index, String studentNo);

  /**
   * 与えられたインデックスの出席種別の値を設定します。
   * 
   * @param index インデックス
   * @param attendenceTypeIndex 出席す別のインデックス。
   *          {@link Presenter#getChoosableAttendenceTypes()}の配列中のインデックスです。
   */
  void setAttendenceType(int index, int attendenceTypeIndex);

  /**
   * 出席一覧ビューのプレゼンターを表すインターフェースです。
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

    /**
     * 選択可能な出席種別を取得します。
     * 
     * @return 選択可能な出席種別
     */
    String[] getChoosableAttendenceTypes();
  }

}
