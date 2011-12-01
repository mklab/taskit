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
