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
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.HelpCall;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * @see HelpCall
 * @author Yuhi Ishikura
 */
@SuppressWarnings("javadoc")
@Service(HelpCall.class)
public interface HelpCallRequest extends RequestContext {

  // for students
  Request<Void> call(String message);

  // for students
  Request<Void> uncall();

  // for students
  Request<Boolean> isCalling();

  // for TAs and teachers
  Request<Void> cancelCall(String accountId);

  Request<List<HelpCallProxy>> getAllHelpCalls();

  Request<List<HelpCallListItemProxy>> getHelpCallListItems();

  Request<Integer> getHelpCallCount();

  Request<Integer> getPosition();
}
