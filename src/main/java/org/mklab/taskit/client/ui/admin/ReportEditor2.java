/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import com.google.gwt.user.client.ui.IsWidget;


/**
 * 課題を編集するビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 28, 2011
 */
public interface ReportEditor2 extends IsWidget {

  /**
   * {@code index}番目の講義タイトルを{@code title}に変更します。
   * 
   * @param index 変更する講義のインデックス
   * @param title 新しい講義名
   */
  void setLecureTitle(int index, String title);

  /**
   * {@code index}番目の講義の{@code reportIndex}番目のタイトルを{@code title}に変更します。
   * 
   * @param lectureIndex 変更する講義のインデックス
   * @param reportIndex 変更する課題のインデックス
   * @param title 新しい課題名
   */
  void setReportTitle(int lectureIndex, int reportIndex, String title);

  /**
   * {@link ReportEditor2}のPresenterを表すインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Feb 28, 2011
   */
  public static interface Presenter {

    /**
     * 講義タイトルを設定します。
     * 
     * @param index 講義インデックス
     * @param title 講義タイトル
     */
    void setLectureTitle(int index, String title);

    /**
     * 課題名を設定します。
     * 
     * @param lectureIndex 講義インデックス
     * @param reportIndex 課題インデックス
     * @param title 課題名
     */
    void setReportTitle(int lectureIndex, int reportIndex, String title);
  }

}
