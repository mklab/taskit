/**
 * 
 */
package org.mklab.taskit.client.activity;

/**
 * @author ishikura
 */
public interface HelpCallObserver {

  /**
   * ヘルプコールがなされたときに、呼び出されます。
   * 
   * @param count ヘルプコール数
   */
  void helpCallCountChanged(int count);

}
