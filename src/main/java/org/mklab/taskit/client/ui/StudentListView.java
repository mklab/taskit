/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.RecordProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;
import java.util.Map;


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
   * 選択中のリストデータを設定します。
   * <p>
   * 選択は変更しますが、このときイベントは発生しません。(
   * {@link Presenter#listSelectionChanged(UserProxy)}は呼び出しません。)
   * 
   * @param user 選択するリストデータ
   */
  void setSelectedListData(UserProxy user);

  /**
   * ユーザーの成績を表示します。
   * 
   * @param user ユーザー
   * @param model 成績データ
   */
  void showUserPage(UserProxy user, StudentwiseRecordModel model);

  /**
   * ユーザーの成績の表示をクリアします。
   */
  void clearUserPage();

  /**
   * 与えられた行をハイライトします。
   * 
   * @param rowData ハイライトする行
   */
  void highlightRow(StudentwiseRecordModel.LectureScore rowData);

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
   * 全生徒の成績統計情報を取得します。
   * 
   * @param records 全生徒の成績統計
   */
  void setRecords(Map<String, RecordProxy> records);

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
     * 提出物を削除します。
     * 
     * @param submission 提出物
     */
    void delete(SubmissionProxy submission);

    /**
     * 出席情報を削除します。
     * 
     * @param attendance 出席情報
     */
    void delete(AttendanceProxy attendance);

    /**
     * 出席を記録します。
     * 
     * @param lecture 講義
     * @param type 出席種別
     */
    void attend(LectureProxy lecture, AttendanceType type);

    /**
     * 与えられたユーザーのコールを削除します。
     * 
     * @param user ユーザー
     */
    void uncall(AccountProxy user);

    /**
     * 再読込を行います。
     */
    void reloadUserPage();
  }

}
