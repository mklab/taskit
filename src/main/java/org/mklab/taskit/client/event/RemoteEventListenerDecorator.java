/**
 * 
 */
package org.mklab.taskit.client.event;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;


/**
 * イベント監視の開始・終了を行うインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @param <E> リスナの種類
 */
public interface RemoteEventListenerDecorator<E extends RemoteEventListenerDecorator<E>> extends RemoteEventListener {

  /**
   * 装飾対象のリスナを設定します。
   * 
   * @param listener 装飾対象のリスナ
   */
  void setListener(E listener);

}
