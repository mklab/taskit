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

import java.util.List;


/**
 * @author ishikura
 * @param <E> エンティティの型
 */
public interface EntityEditorView<E> extends TaskitView {

  /**
   * エンティティを設定します。
   * 
   * @param entities エンティティのリスト
   */
  void setEntities(List<E> entities);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter<E> presenter);

  /**
   * {@link EntityEditorView}のプレゼンターです。
   * 
   * @author ishikura
   * @param <E> エンティティの型
   */
  public static interface Presenter<E> {

    /**
     * 新たな講義オブジェクトを生成します。
     * 
     * @return 講義オブジェクト
     */
    E newEntity();

    /**
     * 与えられたオブジェクトの編集可能にしたものを返します。
     * 
     * @param entity 編集したいエンティティ
     * @return 編集可能なエンティティ
     */
    E edit(E entity);

    /**
     * エンティティを削除します。
     * 
     * @param entity 削除するエンティティ
     */
    void delete(E entity);

    /**
     * 変更を保存します。
     * 
     * @param entity エンティティ
     */
    void save(E entity);

  }

}