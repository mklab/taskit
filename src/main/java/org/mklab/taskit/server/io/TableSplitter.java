/**
 * 
 */
package org.mklab.taskit.server.io;

/**
 * @author ishikura
 * @param <T> 分割するオブジェクトの型
 */
interface TableSplitter<T> {

  /**
   * {@link #split(Object)}で分割された各トークンの説明を取得します。
   * 
   * @return 列の説明
   */
  String[] getDescription();

  /**
   * オブジェクトをトークンに分割します。
   * 
   * @param object オブジェクト
   * @return 分割したトークン
   */
  String[] split(T object);

  /**
   * 分割されたトークンをマージしオブジェクトに戻します。
   * 
   * @param tokens トークン
   * @return オブジェクト
   */
  T merge(String[] tokens);

}
