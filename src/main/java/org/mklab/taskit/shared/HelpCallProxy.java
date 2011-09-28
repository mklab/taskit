/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.HelpCall;
import org.mklab.taskit.server.domain.HelpCallLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@SuppressWarnings("javadoc")
@ProxyFor(value = HelpCall.class, locator = HelpCallLocator.class)
public interface HelpCallProxy extends EntityProxy {

  AccountProxy getCaller();

  String getMessage();

}
