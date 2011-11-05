/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.HelpCallListItem;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author Yuhi Ishikura
 */
@ProxyFor(HelpCallListItem.class)
@SuppressWarnings("javadoc")
public interface HelpCallListItemProxy extends ValueProxy {

  HelpCallProxy getHelpCall();

  List<String> getUsersInCharge();

}
