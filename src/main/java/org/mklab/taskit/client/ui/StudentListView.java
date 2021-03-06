/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
   * @param model 成績データ
   */
  void showUserPage(StudentwiseRecordModel model);

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
   * 現在選択中の学生を閲覧中の指導者を設定します。
   * 
   * @param viewers 現在選択中の学生を閲覧中の指導者のIDのリスト`
   */
  void setViewers(List<String> viewers);

  /**
   * 呼び出しキャンセルボタンの有効・無効を切り替えます。
   * 
   * @param uncallable キャンセルが有効かどうか
   */
  void setUncallable(boolean uncallable);

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
