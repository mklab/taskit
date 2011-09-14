/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.IpAddress;


/**
 * @author teshima
 * @version $Revision$, Mar 14, 2011
 */
public class IpAddressDaoImpl extends AbstractDao implements IpAddressDao {

  /**
   * {@link IpAddressDaoImpl}オブジェクトを構築します。
   * 
   * @param entityManager エンティティマネージャ
   */
  public IpAddressDaoImpl(EntityManager entityManager) {
    super(entityManager);
  }

  /**
   * @see org.mklab.taskit.server.dao.IpAddressDao#getAllIpAddress()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<String> getAllIpAddress() {
    final EntityManager entityManager = entityManager();
    final Query query = entityManager.createQuery("SELECT ip.ipAddress FROM IP_ADDRESS ip ORDER BY ip.ipAddressId ASC"); //$NON-NLS-1$
    return query.getResultList();
  }

  /**
   * @see org.mklab.taskit.server.dao.IpAddressDao#registerIpAddress(java.lang.String)
   */
  @Override
  public void registerIpAddress(String ipAddress) throws IpAddressRegistrationException {
    final EntityManager entityManager = entityManager();
    final IpAddress ipAddressInstance = new IpAddress(ipAddress);
    final EntityTransaction t = entityManager.getTransaction();
    t.begin();

    try {
      entityManager.persist(ipAddressInstance);
      t.commit();
    } catch (EntityExistsException e) {
      if (t.isActive()) {
        t.rollback();
      }
      throw new IpAddressRegistrationException("IP address already exists!"); //$NON-NLS-1$

    } catch (Throwable e) {
      try {
        if (t.isActive()) {
          t.rollback();
        }
        throw new IpAddressRegistrationException("failed to register an IP address"); //$NON-NLS-1$
      } catch (Throwable e1) {
        throw new IpAddressRegistrationException("failed to register an IP address ,and rollback failed."); //$NON-NLS-1$
      }
    }
  }

}
