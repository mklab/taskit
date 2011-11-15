/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;


/**
 * ユーザー編集を行うビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface UserEditView extends TaskitView {

  /**
   * 編集可能なユーザーの一覧を設定します。
   * 
   * @param users 編集可能なユーザーの一覧
   */
  void setUserList(List<UserProxy> users);

  /**
   * 与えられたユーザーの編集を行います。
   * 
   * @param user ユーザー
   */
  void setEdittingUser(UserProxy user);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link UserEditView}で利用するプレゼンターです。
   * 
   * @author Yuhi Ishikura
   */
  public static interface Presenter {

    /**
     * ユーザーの変更を反映します。
     * 
     * @param user ユーザー
     */
    void apply(UserProxy user);

    /**
     * 新たなユーザーを生成します。
     * 
     * @param id ユーザーのID
     */
    void createUser(String id);
  }

}
