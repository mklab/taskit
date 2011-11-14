/**
 * 
 */
package org.mklab.taskit.shared.event;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import de.novanic.eventservice.client.event.Event;


/**
 * TAの担当している生徒に変更があった場合に発生するイベントです。
 * <p>
 * 生徒ページをTA、先生が参照したとき、また参照を終えたときに発生します。
 * 
 * @author Yuhi Ishikura
 */
@Invoker({UserType.TA, UserType.TEACHER})
public class CheckMapEvent implements Event {

  /** for serialization. */
  private static final long serialVersionUID = -8232021006002171401L;

}
