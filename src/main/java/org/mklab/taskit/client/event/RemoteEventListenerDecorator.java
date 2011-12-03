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
package org.mklab.taskit.client.event;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;


/**
 * イベント監視の開始・終了を行うインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public class RemoteEventListenerDecorator implements RemoteEventListener {

  private RemoteEventListener listener;

  /**
   * {@link RemoteEventListenerDecorator}オブジェクトを構築します。
   */
  RemoteEventListenerDecorator() {
    // do nothing 
  }

  /**
   * {@link RemoteEventListenerDecorator}オブジェクトを構築します。
   * 
   * @param listener リスナ
   */
  public RemoteEventListenerDecorator(RemoteEventListener listener) {
    if (listener == null) throw new NullPointerException();
    this.listener = listener;
  }

  /**
   * 装飾する対象のリスナーを設定します。
   * 
   * @param listener listener
   */
  public void setListener(RemoteEventListener listener) {
    this.listener = listener;
  }

  /**
   * 装飾する対象のリスナーを取得します。
   * 
   * @return 装飾する対象のリスナー
   */
  public RemoteEventListener getListener() {
    return this.listener;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(Event anEvent) {
    this.listener.apply(anEvent);
  }

}
