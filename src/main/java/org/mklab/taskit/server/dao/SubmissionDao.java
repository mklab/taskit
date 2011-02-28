/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Submission;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public interface SubmissionDao {

  /**
   * "useName"の"lectureId"回の講義の"no"番目の課題の成績を取得します。
   * 
   * @param userName ユーザー名です。
   * @param reportId 講義IDです。
   * @return 評価
   */
  int getEvaluationFromReportId(String userName, int reportId);

  /**
   * ある学生の全ての提出物を取得します。reportID順（昇順）にリストに追加されています。
   * 
   * @param userName ユーザー名
   * @return ある学生の全ての提出物
   */
  List<Submission> getSubmissionsFromUserName(String userName);

  /**
   * 提出物を登録します。
   * 
   * @param submission
   * @throws SubmissionRegistrationException 提出物の登録に失敗した場合
   */
  void registerSubmission(Submission submission) throws SubmissionRegistrationException;

  /**
   * "useName"の"lectureId"回の講義の"no"番目の課題の成績を"evaluation"に変更します。
   * 
   * @param userName ユーザ名です。
   * @param reportId 講義IDです。
   * @param evaluation 評価
   * @param evaluatorId 評価した人のID
   * @param publicComment パブリックコメント
   * @param privateComment プライベートコメント
   * @throws SubmissionRegistrationException 提出物がまだ存在せず、さらに提出物の登録にも失敗した場合
   */
  void setEvaluation(String userName, int reportId, int evaluation, int evaluatorId, String publicComment, String privateComment) throws SubmissionRegistrationException;
}
