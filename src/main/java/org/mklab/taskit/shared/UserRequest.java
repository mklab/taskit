/**
 * 
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
