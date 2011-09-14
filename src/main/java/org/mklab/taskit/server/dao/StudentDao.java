/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Submission;


/**
 * Student関連のDaoです。
 * 
 * @author teshima
 * @version $Revision$, Jan 28, 2011
 */
public interface StudentDao extends Dao {

  /**
   * アカウントのIDから学籍番号を取得します。
   * 
   * @param accountId アカウントのID
   * @return 学籍番号を返す。
   */
  public String getStudentNo(String accountId);

  /**
   * 講義別に全生徒の提出物データを取得します。
   * 
   * @param lectureId 講義ID
   * @return 全生徒の提出物データ
   */
  public List<Submission> getAllStudentsSubmissionFromLectureId(int lectureId);
}
