/**
 * 
 */
package org.mklab.taskit.shared.event;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import de.novanic.eventservice.client.event.Event;


/**
 * 自分のページを閲覧中のユーザーに変更があったときに発生するイベントです。
 * 
 * @author Yuhi Ishikura
 */
@Invoker(UserType.STUDENT)
public class MyViewerEvent implements Event {

  /** for serialization. */
  private static final long serialVersionUID = -3160272900573231825L;

  // no members

}
