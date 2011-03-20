/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;


/**
 * @author teshima
 * @version $Revision$, Mar 14, 2011
 */
public interface IpAddressDao {

  /**
   * 全てのIPアドレスを取得します。
   * 
   * @return IPアドレス
   */
  public List<String> getAllIpAddress();

  /**
   * IPアドレスの新規登録を行ないます。
   * 
   * @param ipAddress IPアドレス
   * @throws IpAddressRegistrationException IPアドレス登録失敗時の例外です
   */
  public void registerIpAddress(String ipAddress) throws IpAddressRegistrationException;
}
