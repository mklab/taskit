/**
 * 
 */
package org.mklab.taskit.shared.event;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;


/**
 * 学生の成績に変更があった場合に発生するイベントです。
 * 
 * @author Yuhi Ishikura
 */
public class MyRecordChangeEvent implements Event {

  /** ドメイン。 */
  public static final Domain DOMAIN = DomainFactory.getDomain("myRecordChange"); //$NON-NLS-1$

  /** for serialization. */
  private static final long serialVersionUID = -5258139335369899430L;

}
