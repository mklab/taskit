/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.UserProxy;


/**
 * @author ishikura
 */
public interface ProfileView extends TaskitView {

  /**
   * 編集するユーザーデータを設定します。
   * 
   * @param user ユーザーデータ
   */
  void setUser(UserProxy user);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link ProfileView}で利用するプレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * パスワードを変更します。
     * 
     * @param currentPassword 現在のパスワード
     * @param newPassword 新しいパスワード
     */
    void changePassword(String currentPassword, String newPassword);

    /**
     * ユーザー名を設定します。
     * 
     * @param name ユーザー名
     */
    void changeUserName(String name);

  }

}
