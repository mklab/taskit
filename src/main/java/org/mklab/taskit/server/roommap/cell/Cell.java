/**
 * 
 */
package org.mklab.taskit.server.roommap.cell;

/**
 * @author Yuhi Ishikura
 */
public class Cell {

  static final Cell EMPTY_CELL = new Cell(null);
  String userId;

  Cell(String userId) {
    super();
    this.userId = userId;
  }

  /**
   * userIdを取得します。
   * 
   * @return userId
   */
  public String getUserId() {
    return this.userId;
  }

  /**
   * existsを取得します。
   * 
   * @return exists
   */
  public boolean isExists() {
    return this.userId != null;
  }

}
