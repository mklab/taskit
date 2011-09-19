package org.mklab.taskit.server.domain;

import javax.persistence.Version;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 * @param <I> IDの型
 */
public abstract class AbstractEntity<I> {

  private Integer version = Integer.valueOf(1);

  /**
   * versionを取得します。
   * 
   * @return version
   */
  @Version
  final Integer getVersion() {
    return this.version;
  }

  final void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * IDを取得します。
   * 
   * @return ID
   */
  public abstract I getId();

  /**
   * IDを設定します。
   * 
   * @param id ID
   */
  abstract void setId(I id);

  @SuppressWarnings("unchecked")
  final Class<I> getIdType() {
    return (Class<I>)getId().getClass();
  }

}
