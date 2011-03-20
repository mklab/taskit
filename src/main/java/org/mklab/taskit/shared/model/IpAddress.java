/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * @author teshima
 * @version $Revision$, Mar 11, 2011
 */
@Entity(name = "IP_ADDRESS")
public final class IpAddress extends LightEntity {

  /** */
  private static final long serialVersionUID = 1L;
  @GeneratedValue
  @Id
  private int ipAddressId = 0;
  /** IPアドレス */
  private String ipAddress;
  
  /**
   * {@link IpAddress}オブジェクトを構築します。
   * 
   * @param ipAddress IPアドレス
   */
  public IpAddress(String ipAddress) {
    super();
    this.ipAddress = ipAddress;
  }
  /**
   * ipAddressIdを取得します。
   *
   * @return ipAddressId
   */
  public int getIpAddressId() {
    return this.ipAddressId;
  }
  
  /**
   * ipAddressIdを設定します。
   *
   * @param ipAddressId ipAddressId
   */
  public void setIpAddressId(int ipAddressId) {
    this.ipAddressId = ipAddressId;
  }
  
  /**
   * ipAddressを取得します。
   *
   * @return ipAddress
   */
  public String getIpAddress() {
    return this.ipAddress;
  }
  
  /**
   * ipAddressを設定します。
   *
   * @param ipAddress ipAddress
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}
