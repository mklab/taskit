package org.mklab.taskit.client.ui;

/**
 * 学生別成績ビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public interface StudentScoreView extends TaskitView {

  /**
   * index番目のタイトルを設定します。
   * 
   * @param index 行インデックス
   * @param title タイトル
   */
  void setTitle(int index, String title);

  /**
   * 評価値を設定します。
   * 
   * @param index 第何回か
   * @param no 何番か
   * @param evaluation 評価値
   */
  void setEvaluation(int index, int no, int evaluation);

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