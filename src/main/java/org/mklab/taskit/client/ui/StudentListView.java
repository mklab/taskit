/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
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
   * ユーザーの成績を表示します。
   * 
   * @param user ユーザー
   * @param model 成績データ
   */
  void showUserPage(UserProxy user, EvaluationTableModel model);

  /**
   * ユーザーの成績の表示をクリアします。
   */
  void clearUserPage();

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * 現在ビューで選択されているユーザーを取得します。
   * 
   * @return 選択されているユーザー
   */
  UserProxy getSelectedUser();

  /**
   * プレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * リストの選択が変更されたときに呼び出されます。
     * 
     * @param selectedUser 選択されているユーザー
     */
    void listSelectionChanged(UserProxy selectedUser);

    /**
     * 提出を行います。
     * 
     * @param report 提出物
     * @param value 得点
     */
    void submit(ReportProxy report, int value);

    /**
     * 出席を記録します。
     * 
     * @param lecture 講義
     * @param type 出席種別
     */
    void attend(LectureProxy lecture, AttendanceType type);

    /**
     * 再読込を行います。
     */
    void reloadUserPage();
  }

}
