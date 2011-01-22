package org.mklab.taskit.model;

import javax.persistence.Entity;
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
  /** アカウントのIDです。 */
  @Id
  private String id;
  /** 問題のIDです。 */
  private String problemId;
  /** 問題を出された日を表します。 */
  private String date;
  /** 提出状況のタイプです。 */
  private int submissionType;
  /** 生徒に公開するコメントです。 */
  private String publicComment;
  /** 生徒に公開しないコメントです。 */
  private String privateComment;
  /** 問題レベルです。 */
  private int level;

  /**
   * {@link Submission}オブジェクトを構築します。
   */
  public Submission() {
    // do nothing. for serialization
  }

  /**
   * Initialize the generated object of {@link Submission}.
   * 
   * @param id 生徒のIDです。
   * @param problemId 問題のIDです。
   * @param date 問題を出された日です。
   * @param submissionType 提出物の状態です。
   */
  public Submission(String id, String problemId, String date, int submissionType) {
    super();
    this.id = id;
    this.problemId = problemId;
    this.date = date;
    this.submissionType = submissionType;
  }

  /**
   * Initialize the generated object of {@link Submission}.
   * 
   * @param id 生徒のIDです。
   * @param problemId 問題のIDです。
   * @param date 問題を出された日です。
   * @param submissionType 提出物の状態です。
   * @param publicComment 生徒に公開するコメントです。
   * @param privateComment 生徒に公開しないコメントです。
   */
  public Submission(String id, String problemId, String date, int submissionType, String publicComment, String privateComment) {
    super();
    this.id = id;
    this.problemId = problemId;
    this.date = date;
    this.submissionType = submissionType;
    this.publicComment = publicComment;
    this.privateComment = privateComment;
  }

  /**
   * 生徒のIDを設定します。
   * 
   * @param id id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 問題IDを設定します。
   * 
   * @param problemId problemId
   */
  public void setProblemId(String problemId) {
    this.problemId = problemId;
  }

  /**
   * 提出物のタイプを設定します。
   * 
   * @param submissionType submissionType
   */
  public void setSubmissionType(int submissionType) {
    this.submissionType = submissionType;
  }

  /**
   * 生徒のIDを取得します。
   * 
   * @return 生徒のIDです。
   */
  public String getId() {
    return this.id;
  }

  /**
   * 問題のIDを取得します。
   * 
   * @return 問題のIDです。
   */
  public String getProblemId() {
    return this.problemId;
  }

  /**
   * 問題を出された日を取得します。
   * 
   * @return 問題を出された日です。
   */
  public String getDate() {
    return this.date;
  }

  /**
   * 問題を出された日を設定します。
   * 
   * @param date 問題を出された日です。
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * 提出物の状態を取得します。
   * 
   * @return 提出物の状態です。
   */
  public int getSubmissionType() {
    return this.submissionType;
  }

  /**
   * 生徒に公開するコメントを設定します。
   * 
   * @param publicComment 生徒に公開するコメントです。
   */
  public void setPublicComment(String publicComment) {
    this.publicComment = publicComment;
  }

  /**
   * 生徒に公開するコメントを取得します。
   * 
   * @return 生徒に公開するコメントです。
   */
  public String getPublicComment() {
    return this.publicComment;
  }

  /**
   * 生徒に公開しないコメントを設定します。
   * 
   * @param privateComment 生徒に公開しないコメントです。
   */
  public void setPrivateComment(String privateComment) {
    this.privateComment = privateComment;
  }

  /**
   * 生徒に公開しないコメントを取得します。
   * 
   * @return 生徒に公開しないコメントです。
   */
  public String getPrivateComment() {
    return this.privateComment;
  }

  /**
   * 提出の重要度(重み付け)を取得します。
   * 
   * @return レベル
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * 提出の重要度(重み付け)を設定します。
   * 
   * @param level レベル
   */
  public void setLevel(int level) {
    this.level = level;
  }

}
