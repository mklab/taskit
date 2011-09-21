package org.mklab.taskit.server.domain;

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
    return ServiceUtil.findEntity(clazz, id);
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
