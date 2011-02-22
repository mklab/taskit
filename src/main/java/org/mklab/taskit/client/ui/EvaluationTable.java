/**
 * 
 */
package org.mklab.taskit.client.ui;

/**
 * 成績評価を表示する表を表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 10, 2011
 */
public interface EvaluationTable {

  /**
   * 評価項目数を設定します。
   * 
   * @param columnCount 列数
   */
  void setColumnCount(int columnCount);

  /**
   * 行数を設定します。
   * 
   * @param rowCount 行数
   */
  void setRowCount(int rowCount);

  /**
   * 行ヘッダを設定します。
   * 
   * @param rowIndex 行のインデックス
   * @param header 行ヘッダ
   */
  void setRowHeader(int rowIndex, String header);

  /**
   * 列ヘッダを設定します。
   * 
   * @param columnIndex 列インデックス
   * @param header 列ヘッダ
   */
  void setColumnHeader(int columnIndex, String header);

  /**
   * 評価値を設定します。
   * 
   * @param rowIndex 行インデックス
   * @param columnIndex 列インデックス
   * @param evaluation 評価値
   */
  void setEvaluation(int rowIndex, int columnIndex, int evaluation);

  /**
   * 指定された行の列数を設定します。
   * 
   * @param rowIndex 行
   * @param columnCount 列数
   */
  void setColumnCountAt(int rowIndex, int columnCount);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 31, 2011
   */
  public static interface Presenter {

    /**
     * 成績が変更されたときに呼び出されます。
     * 
     * @param index 行インデックス
     * @param no 問題ナンバー
     * @param evaluation 評価値
     */
    void onEvaluationChange(int index, int no, int evaluation);
  }

}
