/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.HelpCallListItemProxy;
import org.mklab.taskit.shared.HelpCallProxy;

import java.util.List;


/**
 * 生徒からの呼び出し一覧を表示するビューです。
 * 
 * @author ishikura
 */
public interface HelpCallListView extends TaskitView {

  /**
   * 呼び出し一覧を設定します。
   * 
   * @param helpCalls 呼び出し一覧
   */
  void setHelpCalls(List<HelpCallListItemProxy> helpCalls);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link HelpCallListView}のプレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * 呼び出し一覧のアイテムが選択されたときに呼び出されます。
     * 
     * @param selectedHelpCall 選択された呼び出し
     */
    void helpCallSelected(HelpCallProxy selectedHelpCall);

    /**
     * チェックインリストに遷移します。
     */
    void goToCheckInList();

  }

}
