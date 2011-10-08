/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.user.client.ui.Widget;


/**
 * ページを整形するクラスです。
 * 
 * @author ishikura
 */
public interface PageLayout {

  /**
   * ページを構築します。
   * 
   * @param taskitView メインコンテンツ
   * @param loginUser ログインユーザー
   * @return ページ
   */
  Widget layout(TaskitView taskitView, UserProxy loginUser);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * プレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * ログアウトします。
     */
    void logout();
  }

}
