/**
 * 
 */
package org.mklab.taskit.client.event;

/**
 * タイムアウトが発生した時に復帰する機能を提供するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface TimeoutRecoverable {

  /**
   * クライアントサイドでイベント待機時にタイムアウトが発生したときに呼び出されます。
   * 
   * @throws TimeoutRecoveryFailureException 復帰できなかった場合
   */
  void recoverFromTimeout() throws TimeoutRecoveryFailureException;

}
