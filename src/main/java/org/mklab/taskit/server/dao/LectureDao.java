/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Lecture;


/**
 * @author teshima
 * @version $Revision$, Jan 31, 2011
 */
public interface LectureDao extends Dao {

  /**
   * 全ての講義のデータを取得します。
   * 
   * @return 全ての講義のデータです。
   */
  public List<Lecture> getAllLectures();

  /**
   * 講義を登録します。
   * 
   * @param lecture 講義データ
   */
  public void registerLecture(Lecture lecture);

  /**
   * 講義数を取得します。
   * 
   * @return 講義数
   */
  public int getLectureCount();
}
