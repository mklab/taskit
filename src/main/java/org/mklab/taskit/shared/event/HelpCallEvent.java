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
package org.mklab.taskit.shared.event;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.Date;

import de.novanic.eventservice.client.event.Event;


/**
 * ヘルプコールが行われたときに発生するイベントです。
 * 
 * @author Yuhi Ishikura
 */
@Invoker({UserType.TA, UserType.TEACHER})
public class HelpCallEvent implements Event {

  /**
   * ヘルプコールの変更種別を表す列挙型です。
   * 
   * @author Yuhi Ishikura
   */
  public static enum Type {
    /**
     * 追加されたときに設定される列挙型定数です。
     */
    ADDED,
    /**
     * 削除されたときに設定される列挙型定数です。
     */
    DELETED
  }

  /** for serialization. */
  private static final long serialVersionUID = 2376190021474079729L;

  private int helpCallCount;
  private String callerId;
  private String message;
  private Date date;
  private Type type;

  /**
   * {@link HelpCallEvent}オブジェクトを構築します。
   */
  public HelpCallEvent() {
    // only for serialization.
  }

  /**
   * {@link HelpCallEvent}オブジェクトを構築します。
   * 
   * @param date 呼び出された日時
   * @param callerId 呼び出している生徒のID
   * @param message メッセージ
   * @param type 追加なのか削除なのか
   * @param helpCallCount 現在のヘルプコール数
   */
  public HelpCallEvent(Date date, String callerId, String message, Type type, int helpCallCount) {
    this.date = date;
    this.callerId = callerId;
    this.message = message;
    this.type = type;
    this.helpCallCount = helpCallCount;
  }

  /**
   * 現在のヘルプコール数を取得します。
   * 
   * @return helpCallCount ヘルプコール数
   */
  public int getHelpCallCount() {
    return this.helpCallCount;
  }

  /**
   * メッセージを取得します。
   * 
   * @return メッセージ
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * 呼び出している生徒のIDを取得します。
   * 
   * @return callerId 呼び出している生徒のID
   */
  public String getCallerId() {
    return this.callerId;
  }

  /**
   * 呼び出しされた日時を取得します。
   * 
   * @return 呼び出された日時
   */
  public Date getDate() {
    return this.date;
  }

  /**
   * 変更の種別を取得します。
   * 
   * @return type 変更の種別
   */
  public Type getType() {
    return this.type;
  }
}
