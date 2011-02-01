/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ui.admin.NewAccountView;

/**
 * 管理者用ビューを提供するクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public interface AdminView extends TaskitView {

  /**
   * 新規アカウント作成ビューを取得します。
   * 
   * @return 新規アカウント作成ビュー
   */
  NewAccountView getNewAccountView();
}
