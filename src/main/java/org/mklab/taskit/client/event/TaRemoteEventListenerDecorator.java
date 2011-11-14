/**
 * 
 */
package org.mklab.taskit.client.event;

import org.mklab.taskit.shared.event.HelpCallEvent;

import de.novanic.eventservice.client.event.Event;


/**
 * TA用のリモートイベントリスナです。
 * 
 * @author Yuhi Ishikura
 */
public class TaRemoteEventListenerDecorator implements RemoteEventListenerDecorator<TaRemoteEventListenerDecorator> {

  private TaRemoteEventListenerDecorator listener;

  /**
   * {@inheritDoc}
   */
  @Override
  public final void apply(Event event) {
    if (event instanceof HelpCallEvent) {
      helpCallChanged((HelpCallEvent)event);
    }
  }

  /**
   * ヘルプコールに変更があったときに呼び出されます。
   * 
   * @param evt イベント
   */
  public void helpCallChanged(HelpCallEvent evt) {
    if (this.listener != null) this.listener.helpCallChanged(evt);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListener(TaRemoteEventListenerDecorator listener) {
    this.listener = listener;
  }

}