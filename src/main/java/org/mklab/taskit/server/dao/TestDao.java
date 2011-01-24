package org.mklab.taskit.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mklab.taskit.shared.model.Test;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class TestDao {

  private EntityManager manager;

  /**
   * {@link TestDao}オブジェクトを構築します。
   * 
   * @param manager エンティティマネージャ
   */
  public TestDao(EntityManager manager) {
    this.manager = manager;
  }

  /**
   * 全てのTestインスタンスを列挙します。
   * 
   * @return 全てのTestインスタンス
   */
  @SuppressWarnings("unchecked")
  public List<Test> list() {
    final Query q = this.manager.createQuery("select t from test as t"); //$NON-NLS-1$
    return q.getResultList();
  }

}
