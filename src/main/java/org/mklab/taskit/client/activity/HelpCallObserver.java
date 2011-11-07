/**
 * 
 */
package org.mklab.taskit.client.activity;

/**
 * ヘルプコール数を監視するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface HelpCallObserver {

  /**
   * ヘルプコールがなされたときに、呼び出されます。
   * 
   * @param count ヘルプコール数
   */
  void helpCallCountChanged(int count);

}
