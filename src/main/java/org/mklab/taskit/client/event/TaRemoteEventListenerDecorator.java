/**
 * 
 */
package org.mklab.taskit.client.event;

import org.mklab.taskit.shared.event.CheckMapEvent;
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
    } else if (event instanceof CheckMapEvent) {
      checkMapChanged((CheckMapEvent)event);
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
   * TA、先生の、生徒の担当が変更されたときに呼び出されます。
   * 
   * @param evt イベント
   */
  public void checkMapChanged(CheckMapEvent evt) {
    if (this.listener != null) this.listener.checkMapChanged(evt);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListener(TaRemoteEventListenerDecorator listener) {
    this.listener = listener;
  }

}