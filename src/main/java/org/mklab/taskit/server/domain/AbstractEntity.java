package org.mklab.taskit.server.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

  /**
   * データベースに変更を反映します。
   * <p>
   * IDを変更してこのメソッドを呼び出すと、新たなエンティティとして登録されてしまうため注意してください。
   */
  public void update() {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    t.begin();
    try {
      em.merge(this);
      t.commit();
    } catch (Throwable ex) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * データベースにエンティティを登録します。
   */
  public void persist() {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    try {
      t.begin();
      em.persist(this);
      t.commit();
    } catch (Throwable ex) {
      t.rollback();
    } finally {
      em.close();
    }
  }
}
