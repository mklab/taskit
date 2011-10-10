/**
 * 
 */
package org.mklab.taskit.client.ui;

/**
 * @author ishikura
 */
public interface HelpCallDisplayable {

  /**
   * 呼び出されたときに、呼び出されるメソッドです。
   * 
   * @param count 呼び出し件数
   */
  void showHelpCallCount(int count);

  /**
   * 表示・非表示を切り替えます。
   * 
   * @param enabled 表示するかどうか
   */
  void setHelpCallDisplayEnabled(boolean enabled);

}
