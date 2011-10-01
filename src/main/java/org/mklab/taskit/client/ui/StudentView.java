/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.model.StudentScoreModel;


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
  void setModel(StudentScoreModel model);

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
  void highlightRow(StudentScoreModel.LectureScore rowData);

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
  }

}
