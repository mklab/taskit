package org.mklab.taskit.server.dao;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public interface Dao {

  /**
   * DAOのリソースを開放します。
   * <p>
   * 以降このDAOのインスタンスは利用できなくなります。
   * 
   * @throws IllegalStateException 開放できない状態だった場合
   */
  void close();

}
