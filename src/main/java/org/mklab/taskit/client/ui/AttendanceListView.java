/**
 * 
 */
package org.mklab.taskit.client.ui;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public interface AttendanceListView extends TaskitView {

  /**
   * 選択可能な講義数を設定します。
   * 
   * @param lectureCount 選択可能な講義数
   */
  void setLectures(int lectureCount);

  /**
   * 選択可能な出席種別の値を設定します。
   * 
   * @param attendanceTypes 選択可能な出席種別の値
   */
  void setAttendanceTypes(String[] attendanceTypes);

  /**
   * 選択されている講義を設定します。
   * 
   * @param index 選択する講義のインデックス
   */
  void setSelectedLecture(int index);

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
   * @param attendanceTypeIndex 出席す別のインデックス
   */
  void setAttendanceType(int index, int attendanceTypeIndex);

  /**
   * 出席一覧ビューのプレゼンターを表すインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 30, 2011
   */
  public static interface Presenter {

    /**
     * index番目の学生の出席種別を変更します。
     * 
     * @param index 表中の学生のインデックス
     * @param attendanceTypeIndex 表中の出席種別のインデックス
     */
    void attendanceTypeEditted(int index, int attendanceTypeIndex);

    /**
     * 選択されている講義が変更されたときに呼び出されます。
     * 
     * @param selectedLectureIndex 選択されている講義のインデックス
     */
    void lectureSelectionChanged(int selectedLectureIndex);

    /**
     * 学生番号がクリックされたときに呼び出されます。
     * 
     * @param index クリックされた行インデックス
     */
    void studentNumberClicked(int index);

  }

}
