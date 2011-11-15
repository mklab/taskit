package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.User;
import org.mklab.taskit.server.domain.UserLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * @see User
 * @author ishikura
 * @version $Revision$, 2011/09/18
 */
@ProxyFor(value = User.class, locator = UserLocator.class)
public interface UserProxy extends EntityProxy {

  /**
   * アカウントIDを取得します。
   * 
   * @return アカウントID
   */
  AccountProxy getAccount();

  /**
   * ユーザー種別を取得します。
   * 
   * @return ユーザー種別
   */
  UserType getType();

  /**
   * ユーザー名を取得します。
   * 
   * @return ユーザー名
   */
  String getName();

  /**
   * ユーザー名を設定します。
   * 
   * @param name ユーザー名
   */
  void setName(String name);

}
