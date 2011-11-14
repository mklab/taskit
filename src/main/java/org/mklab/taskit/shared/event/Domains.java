/**
 * 
 */
package org.mklab.taskit.shared.event;

import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;


/**
 * GWTEventServiceのイベントドメインを定義したクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class Domains {

  /** TAに関係するイベントのドメインです。 */
  public static final Domain TA = DomainFactory.getDomain("ta"); //$NON-NLS-1$
  /** 先生に関係するイベントのドメインです。 */
  public static final Domain TEACHER = DomainFactory.getDomain("teacher"); //$NON-NLS-1$
  /** 生徒に関係するイベントのドメインです。 */
  public static final Domain STUDENT = DomainFactory.getDomain("student"); //$NON-NLS-1$

}
