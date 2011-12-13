/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
