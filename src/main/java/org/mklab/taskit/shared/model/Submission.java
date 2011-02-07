package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * 提出物を表すクラスです。
 * 
 * @author teshima
 * @version $Revision$, 2011/01/19
 */
@Entity(name = "SUBMISSION")
public class Submission extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = 594166644251110376L;
  @Id
  @GeneratedValue
  private int submissionId;
  /** 問題のIDです。 */
  private int reportId;
  /** 提出された日時(ms)です。 */
  private long time;
  /** 提出者のアカウントのIDです。 */
  private String userName;
  /** この提出に対する評価値です。 */
  private int evaluation;
  /** この提出の評価を行ったユーザーのアカウントIDです。 */
  private int evaluatorId;

  /** 生徒に公開するコメントです。 */
  private String publicComment;
  /** 生徒に公開しないコメントです。 */
  private String privateComment;

  /**
   * {@link Submission}オブジェクトを構築します。
   */
  public Submission() {
    // do nothing. for serialization
  }

  /**
   * {@link Submission}オブジェクトを構築します。
   * 
   * @param reportId 問題ID
   * @param time 提出時間
   * @param userName 提出者ID
   * @param evaluation 評価
   * @param evaluatorId 評価したユーザーのアカウントID
   * @param publicComment 生徒に公開するコメント
   * @param privateComment 生徒に非公開のコメント
   */
  public Submission(int reportId, long time, String userName, int evaluation, int evaluatorId, String publicComment, String privateComment) {
    this.reportId = reportId;
    this.time = time;
    this.userName = userName;
    this.evaluation = evaluation;
    this.evaluatorId = evaluatorId;
    this.publicComment = publicComment;
    this.privateComment = privateComment;
  }

  /**
   * submissionIdを取得します。
   * 
   * @return submissionId
   */
  public int getSubmissionId() {
    return this.submissionId;
  }

  /**
   * submissionIdを設定します。
   * 
   * @param submissionId submissionId
   */
  public void setSubmissionId(int submissionId) {
    this.submissionId = submissionId;
  }

  /**
   * reportIdを取得します。
   * 
   * @return reportId
   */
  public int getReportId() {
    return this.reportId;
  }

  /**
   * reportIdを設定します。
   * 
   * @param reportId reportId
   */
  public void setReportId(int reportId) {
    this.reportId = reportId;
  }

  /**
   * timeを取得します。
   * 
   * @return time
   */
  public long getTime() {
    return this.time;
  }

  /**
   * timeを設定します。
   * 
   * @param time time
   */
  public void setTime(long time) {
    this.time = time;
  }

  /**
   * accountIdを取得します。
   * 
   * @return accountId
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * accountIdを設定します。
   * 
   * @param accountId accountId
   */
  public void setUserName(String accountId) {
    this.userName = accountId;
  }

  /**
   * evaluationを取得します。
   * 
   * @return evaluation
   */
  public int getEvaluation() {
    return this.evaluation;
  }

  /**
   * evaluationを設定します。
   * 
   * @param evaluation evaluation
   */
  public void setEvaluation(int evaluation) {
    this.evaluation = evaluation;
  }

  /**
   * evaluatorIdを取得します。
   * 
   * @return evaluatorId
   */
  public int getEvaluatorId() {
    return this.evaluatorId;
  }

  /**
   * evaluatorIdを設定します。
   * 
   * @param evaluatorId evaluatorId
   */
  public void setEvaluatorId(int evaluatorId) {
    this.evaluatorId = evaluatorId;
  }

  /**
   * publicCommentを取得します。
   * 
   * @return publicComment
   */
  public String getPublicComment() {
    return this.publicComment;
  }

  /**
   * publicCommentを設定します。
   * 
   * @param publicComment publicComment
   */
  public void setPublicComment(String publicComment) {
    this.publicComment = publicComment;
  }

  /**
   * privateCommentを取得します。
   * 
   * @return privateComment
   */
  public String getPrivateComment() {
    return this.privateComment;
  }

  /**
   * privateCommentを設定します。
   * 
   * @param privateComment privateComment
   */
  public void setPrivateComment(String privateComment) {
    this.privateComment = privateComment;
  }

}
