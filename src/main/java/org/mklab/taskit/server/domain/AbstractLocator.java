package org.mklab.taskit.server.domain;

import javax.persistence.EntityManager;

import com.google.web.bindery.requestfactory.shared.Locator;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 * @param <T> 配置するエンティティの型
 * @param <I> 配置するエンティティのキーの型
 */
public abstract class AbstractLocator<T extends AbstractEntity<I>, I> extends Locator<T, I> {

  /**
   * {@inheritDoc}
   */
  @Override
  public T create(Class<? extends T> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T find(Class<? extends T> clazz, I id) {
    return findEntity(clazz, id);
  }

  static <T, I> T findEntity(Class<? extends T> clazz, I id) {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      return em.find(clazz, id);
    } finally {
      em.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public I getId(T domainObject) {
    return domainObject.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getVersion(T domainObject) {
    return domainObject.getVersion();
  }

}
