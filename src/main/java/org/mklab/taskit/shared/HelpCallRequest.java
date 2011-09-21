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

  Request<Void> call(String message);

  Request<List<HelpCallProxy>> getAllHelpCalls();

}
