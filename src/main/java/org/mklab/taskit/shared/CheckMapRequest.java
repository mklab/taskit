/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.CheckMap;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author Yuhi Ishikura
 */
@Service(CheckMap.class)
@SuppressWarnings("javadoc")
public interface CheckMapRequest extends RequestContext {

  Request<Void> checkIn(AccountProxy student);

  Request<Void> checkOut();

  Request<AccountProxy> getTargetStudentOf(AccountProxy user);

  Request<List<String>> getUsersInCheck(AccountProxy student);

  Request<List<CheckMapProxy>> getAllCheckMap();
}
