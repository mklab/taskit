/**
 * 
 */
package org.mklab.taskit.client.ui;

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