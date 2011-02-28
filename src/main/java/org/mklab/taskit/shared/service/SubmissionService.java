/**
 * 
 */
package org.mklab.taskit.shared.service;

import org.mklab.taskit.shared.dto.StudentwiseScoresDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * 提出管理操作を提供するサービスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
@RemoteServiceRelativePath("submission")
public interface SubmissionService extends RemoteService {

  /**
   * 学生の全ての成績データを取得します。
   * 
   * @param userName ユーザー名
   * @return 成績データ
   */
  StudentwiseScoresDto getStudentwiseScores(String userName);

  /**
   * 成績を設定します。
   * 
   * @param userName 学生のユーザー名
   * @param lectureIndex 講義インデックス
   * @param reportIndex 課題インデックス
   * @param evaluation 評価値
   * @param publicComment 生徒にも公開するコメント
   * @param privateComment 生徒には非公開のコメント
   * @throws ServiceException 提出物がまだ存在せず、また新たに登録することもできなかった場合
   */
  void setEvaluation(String userName, int lectureIndex, int reportIndex, int evaluation, String publicComment, String privateComment) throws ServiceException;
}
