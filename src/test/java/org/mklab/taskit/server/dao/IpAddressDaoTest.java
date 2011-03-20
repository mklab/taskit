/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;


/**
 * @author teshima
 * @version $Revision$, Mar 14, 2011
 */
public class IpAddressDaoTest extends DaoTest {

  /**
   * 全IPアドレスを取得できるかどうかテストします。
   * 
   * @throws IpAddressRegistrationException IPアドレス登録失敗時の例外
   */
  @Test
  public void testGetAllIpAddress() throws IpAddressRegistrationException {
    final IpAddressDao ipAddressDao = new IpAddressDaoImpl(createEntityManager());
    ipAddressDao.registerIpAddress("131.206.48.1"); //$NON-NLS-1$
    ipAddressDao.registerIpAddress("131.206.48.2"); //$NON-NLS-1$
    ipAddressDao.registerIpAddress("131.206.48.3"); //$NON-NLS-1$
    ipAddressDao.registerIpAddress("131.206.48.4"); //$NON-NLS-1$
    ipAddressDao.registerIpAddress("131.206.48.5"); //$NON-NLS-1$

    List<String> ipAddresses = ipAddressDao.getAllIpAddress();
    assertEquals("131.206.48.1", ipAddresses.get(0)); //$NON-NLS-1$
    assertEquals("131.206.48.2", ipAddresses.get(1)); //$NON-NLS-1$
    assertEquals("131.206.48.3", ipAddresses.get(2)); //$NON-NLS-1$
    assertEquals("131.206.48.4", ipAddresses.get(3)); //$NON-NLS-1$
    assertEquals("131.206.48.5", ipAddresses.get(4)); //$NON-NLS-1$

  }
}
