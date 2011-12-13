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

import org.mklab.taskit.client.model.AttendanceListItem;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;

import java.util.List;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public interface AttendanceListView extends TaskitView {

  /**
   * 講義データを設定します。
   * 
   * @param lectures 講義データ
   */
  void setLectures(List<LectureProxy> lectures);

  /**
   * 出席データを設定します。
   * 
   * @param attendances 出席データ
   */
  void setAttendances(List<AttendanceListItem> attendances);

  /**
   * 選択中の講義データを取得します。
   * 
   * @return 選択中の講義データ
   */
  LectureProxy getSelectedLecture();

  /**
   * 選択中の講義データを設定します。
   * 
   * @param lecture 講義データ
   */
  void setSelectedLecture(LectureProxy lecture);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * 出席一覧ビューのプレゼンターを表すインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 30, 2011
   */
  public static interface Presenter {

    /**
     * 選択されている講義が変更されたときに呼び出されます。
     * 
     * @param selectedLectureIndex 選択されている講義
     */
    void lectureSelectionChanged(LectureProxy selectedLectureIndex);

    /**
     * 出席状況を記録します。
     * 
     * @param user ユーザー
     * @param type 出席種別
     */
    void attend(AccountProxy user, AttendanceType type);

  }

}
