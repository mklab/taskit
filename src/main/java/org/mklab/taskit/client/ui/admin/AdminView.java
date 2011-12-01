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
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.event.dom.client.HasClickHandlers;


/**
 * 管理者用のビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface AdminView extends TaskitView {

  /**
   * 講義データ編集アクティビティのリンクを取得します。
   * 
   * @return 講義データ編集アクティビティのリンク
   */
  HasClickHandlers getLectureEditorLink();

  /**
   * 課題データ編集アクティビティのリンクを取得します。
   * 
   * @return 課題データ編集アクティビティのリンク
   */
  HasClickHandlers getReportEditorLink();

  /**
   * ユーザーデータ編集アクティビティへのリンクを取得します。
   * 
   * @return ユーザーデータ編集アクティビティへのリンク
   */
  HasClickHandlers getUserEditorLink();

}
