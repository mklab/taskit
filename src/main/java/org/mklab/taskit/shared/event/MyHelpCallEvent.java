/**
 * 
 */
package org.mklab.taskit.shared.event;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import de.novanic.eventservice.client.event.Event;


/**
 * 学生のヘルプコールの状態が変更されたときに発生するイベントです。
 * 
 * @author Yuhi Ishikura
 */
@Invoker({UserType.STUDENT})
public class MyHelpCallEvent implements Event {

  /** for serialization. */
  private static final long serialVersionUID = 8131384335594347282L;

  /**
   * {@link MyHelpCallEvent}オブジェクトを構築します。
   */
  public MyHelpCallEvent() {
    // only for serialization.
  }

}
