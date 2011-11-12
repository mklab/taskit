/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.User;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see user
 * @author ishikura
 */
@Service(User.class)
@SuppressWarnings("javadoc")
public interface UserRequest extends RequestContext {

  Request<Void> changeUserName(String userName);

  Request<UserProxy> getUserByAccountId(String accountId);

  Request<UserProxy> getLoginUser();

  Request<List<UserProxy>> getAllStudents();

}
