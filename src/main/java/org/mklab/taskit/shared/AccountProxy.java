package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.AccountLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * アカウントを表すインターフェースです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/18
 */
@ProxyFor(value = Account.class, locator = AccountLocator.class)
public interface AccountProxy extends EntityProxy {

  /**
   * ログインIDを取得します。
   * 
   * @return ログインID
   */
  String getId();

}
