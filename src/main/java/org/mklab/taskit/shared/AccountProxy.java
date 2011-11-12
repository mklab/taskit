package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.AccountLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * @see Account
 * @author Yuhi Ishikura
 */
@ProxyFor(value = Account.class, locator = AccountLocator.class)
@SuppressWarnings("javadoc")
public interface AccountProxy extends EntityProxy {

  String getId();

}
