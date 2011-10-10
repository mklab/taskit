/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.user.client.Timer;


/**
 * メッセージの通知を行うクラスです。
 * 
 * @author ishikura
 */
public final class MessageDisplay {

  /** メッセージを表示後非表示にするまでの時間(ms)です。*/
  private static final int MESSAGE_TIMEOUT_MILLIS = 10000;
  private Target target;
  private Timer timer;

  /**
   * {@link MessageDisplay}オブジェクトを構築します。
   * 
   * @param target メッセージ表示先
   */
  public MessageDisplay(final Target target) {
    if (target == null) throw new NullPointerException();
    this.target = target;
    this.timer = new Timer() {

      @Override
      public void run() {
        target.clearMessage();
      }
    };
  }

  /**
   * メッセージを表示します。
   * 
   * @param message メッセージ
   */
  public void showMessage(String message) {
    this.timer.cancel();
    this.target.setMessage(message);
    clearMessageLater();
  }

  /**
   * メッセージをクリアします。
   */
  public void clearMessage() {
    this.timer.cancel();
    this.target.setMessage(""); //$NON-NLS-1$
  }

  private void clearMessageLater() {
    this.timer.schedule(MESSAGE_TIMEOUT_MILLIS);
  }

  /**
   * メッセージ表示先を表すインターフェースです。
   * 
   * @author ishikura
   */
  public static interface Target {

    /**
     * メッセージを設定します。
     * 
     * @param message メッセージ
     */
    void setMessage(String message);

    /**
     * メッセージの表示を消します。
     */
    void clearMessage();
  }
}
