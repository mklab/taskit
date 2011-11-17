package org.mklab.taskit.server.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Version;

import org.apache.log4j.Logger;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 * @param <I> IDの型
 */
public abstract class AbstractEntity<I> {

  private Integer version = Integer.valueOf(1);
  /** インスタンスで利用するロガーです。 */
  private static Logger logger = Logger.getLogger(AbstractEntity.class);

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
  public abstract void setId(I id);

  @SuppressWarnings("unchecked")
  final Class<I> getIdType() {
    return (Class<I>)getId().getClass();
  }

  /**
   * 予期せぬ例外のログを取ります。
   * 
   * @param e 例外
   */
  protected static final void logThrown(Throwable e) {
    logger.error("Unexpected exception.", e); //$NON-NLS-1$
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
      logThrown(ex);
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
      logThrown(ex);
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * エンティティがすでに存在すればそれを更新し、まだ存在しなければ新しく保存します。
   */
  public void updateOrCreate() {
    final EntityManager em = EMF.get().createEntityManager();
    if (getId() != null && em.find(getClass(), getId()) != null) {
      update();
    } else {
      persist();
    }
  }

  /**
   * エンティティをデータベースから削除します。
   */
  public void delete() {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    try {
      t.begin();
      final AbstractEntity<?> entity = em.find(getClass(), getId());
      if (entity != null) {
        em.remove(entity);
      }
      t.commit();
    } catch (Throwable ex) {
      logThrown(ex);
      t.rollback();
    } finally {
      em.close();
    }
  }
}
