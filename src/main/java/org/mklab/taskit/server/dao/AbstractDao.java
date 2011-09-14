package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;


/**
 * {@link EntityManager}の管理を行う最低限の機能を提供したDAOの基底クラスです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
abstract class AbstractDao implements Dao {

  private EntityManager entityManager;

  AbstractDao(EntityManager entityManager) {
    if (entityManager == null) throw new NullPointerException();
    if (entityManager.isOpen() == false) throw new IllegalStateException();
    this.entityManager = entityManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void close() {
    assertIsOpen();
    this.entityManager.close();
  }

  /**
   * エンティティマネージャを取得します。
   * <p>
   * このメソッドでは新しいエンティティマネージャは作成せず、コンストラクタで与えられたものを再利用します。そのため処理完了後も、
   * エンティティマネージャに対しcloseを行わないでください。
   * 
   * @return エンティティマネージャ
   */
  protected final EntityManager entityManager() {
    assertIsOpen();
    return this.entityManager;
  }

  private void assertIsOpen() {
    if (this.entityManager.isOpen() == false) throw new IllegalStateException("Already closed."); //$NON-NLS-1$
  }

}
