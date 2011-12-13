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

import org.mklab.taskit.server.domain.User;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see User
 * @author ishikura
 */
@Service(User.class)
@SuppressWarnings("javadoc")
public interface UserRequest extends RequestContext {

  Request<Void> changeMyUserName(String userName);

  Request<Void> changeUserName(String accountId, String userName);

  Request<List<UserProxy>> getAllUsers();

  Request<UserProxy> getUserByAccountId(String accountId);

  Request<UserProxy> getLoginUser();

  Request<List<UserProxy>> getAllStudents();

  InstanceRequest<UserProxy, Void> update();

}
