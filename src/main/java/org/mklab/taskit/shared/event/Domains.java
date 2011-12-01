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
