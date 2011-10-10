/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.HelpCall;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


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

  Request<Long> getHelpCallCount();
}
