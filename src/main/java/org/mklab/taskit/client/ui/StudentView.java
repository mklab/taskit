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
import org.mklab.taskit.shared.RecordProxy;
import org.mklab.taskit.shared.UserProxy;


/**
 * 学生が成績データ閲覧に利用するビューです。
 * 
 * @author ishikura
 */
public interface StudentView extends TaskitView {

  /**
   * 学生の成績データを設定します。
   * 
   * @param model 成績データ
   */
  void setModel(StudentwiseRecordModel model);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * 与えられた行をハイライトします。
   * 
   * @param rowData ハイライトする行
   */
  void highlightRow(StudentwiseRecordModel.LectureScore rowData);

  /**
   * 呼び出し中かどうか設定します。
   * 
   * @param calling 呼び出し中かどうか
   */
  void setCalling(boolean calling);

  /**
   * 呼び出し順序を取得します。
   * 
   * @param position 自分が何番目の呼び出しかどうか
   */
  void setHelpCallPosition(int position);

  /**
   * ログインユーザー情報を設定します。
   * 
   * @param loginUser ログインユーザー情報
   */
  void setLoginUser(UserProxy loginUser);

  /**
   * 成績を設定します。
   * 
   * @param record 成績
   */
  void setRecord(RecordProxy record);

  /**
   * {@link StudentView}で利用するプレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * 先生やTAの呼び出しを希望します。
     * 
     * @param message メッセージ
     */
    void call(String message);

    /**
     * 呼び出しをキャンセルします。
     */
    void uncall();
  }

}
