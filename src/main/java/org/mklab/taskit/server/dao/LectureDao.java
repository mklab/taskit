/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;



/**
 * @author teshima
 * @version $Revision$, Jan 31, 2011
 */
public interface LectureDao {
  /**
   * 日付から講義のタイトルを取得します。
   * @param date 日付
   * @return 講義のタイトル
   */
  public String getTitleFromDate(String date);
}
