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

  List<Submission> getAllSubmission(String studentNo);

  /**
   * 提出物を登録します。
   * 
   * @param submission
   * @throws Exception
   */
  void registerSubmission(Submission submission) throws Exception;

  /**
   * "useName"の"lectureId"回の講義の"no"番目の課題の成績を"evaluation"に変更します。
   * 
   * @param userName ユーザ名です。
   * @param reportId 講義IDです。
   * @param evaluation 評価
   */
  void setEvaluation(String userName, int reportId, int evaluation);

  /**
   * "useName"の"lectureId"回の講義の"no"番目の課題の成績を取得します。
   * 
   * @param userName ユーザー名です。
   * @param reportId 講義IDです。
   * @return 評価
   */
  int getEvaluationFromReportId(String userName, int reportId);
}
