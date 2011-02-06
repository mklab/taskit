package org.mklab.taskit.client.ui;

/**
 * 学生別成績ビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public interface StudentScoreView extends TaskitView {

  /**
   * 全講義数を設定します。
   * 
   * @param lectureCount 講義数
   */
  void setLectureCount(int lectureCount);

  /**
   * 講義情報を設定します。
   * 
   * @param index 行インデックス
   * @param reportCount レポート数
   * @param title 講義名
   */
  void setLectureInfo(int index, int reportCount, String title);

  /**
   * 評価値を設定します。
   * 
   * @param index 第何回か
   * @param no 何番か
   * @param evaluation 評価値
   */
  void setEvaluation(int index, int no, int evaluation);

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