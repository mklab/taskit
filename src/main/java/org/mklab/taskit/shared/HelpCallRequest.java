/**
 * 
 */
package org.mklab.taskit.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;


@SuppressWarnings("javadoc")
public interface HelpCallRequest extends RequestContext {

  Request<Void> call(String message);

}
