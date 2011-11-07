/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.CheckMap;
import org.mklab.taskit.server.domain.CheckMapLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@SuppressWarnings("javadoc")
@ProxyFor(value = CheckMap.class, locator = CheckMapLocator.class)
public interface CheckMapProxy extends EntityProxy {

  String getId();

  AccountProxy getStudent();

  Date getDate();

}
