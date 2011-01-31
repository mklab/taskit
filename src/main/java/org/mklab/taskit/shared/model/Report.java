/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * @author teshima
 * @version $Revision$, Jan 25, 2011
 */
@Entity(name = "REPORT")
public final class Report extends LightEntity {

  /** */
  private static final long serialVersionUID = 28409820481L;

  /** 課題のID */
  @Id
  @GeneratedValue
  private int reportId;
  /** 課題の番号 */
  private String no;
  /** この課題の属する講義のIDです。 */
  private int lessonId;
  /** 課題のタイトル */
  private String title;
  /** 課題の詳細 */
  private String detail;
  /** 課題の配点 */
  private int allotment;

  /**
   * {@link Report}オブジェクトを構築します。
   * 
   * @param no 課題の番号
   * @param title 課題の名前
   * @param detail 課題の詳細
   * @param allotment 課題の配点
   * @param lessonId 講義ID
   */
  public Report(String no, String title, String detail, int allotment, int lessonId) {
    this.no = no;
    this.title = title;
    this.detail = detail;
    this.allotment = allotment;
    this.lessonId = lessonId;
  }

  /**
   * 課題IDを設定します。
   * 
   * @param id 課題ID
   */
  public void setId(int id) {
    this.reportId = id;
  }

  /**
   * 課題IDを取得します。
   * 
   * @return 課題ID
   */
  public int getId() {
    return this.reportId;
  }

  /**
   * 課題の名前を設定します。
   * 
   * @param name 課題の名前
   */
  public void setTitle(String name) {
    this.title = name;
  }

  /**
   * 課題の名前を取得します。 　*
   * 
   * @return 課題の名前
   */
  public String getName() {
    return this.title;
  }

  /**
   * 課題の詳細説明を設定します。
   * 
   * @param detail 課題の詳細説明
   */
  public void setDetail(String detail) {
    this.detail = detail;
  }

  /**
   * 課題の詳細説明を取得します。 　*
   * 
   * @return 課題の詳細説明
   */
  public String getDetail() {
    return this.detail;
  }

  /**
   * 課題のレベル（番号）を設定します。
   * 
   * @param no 課題のレベル（番号）
   */
  public void setNo(String no) {
    this.no = no;
  }

  /**
   * 課題のレベル（番号）を取得します。 　*
   * 
   * @return 課題のレベル（番号）
   */
  public String getLevel() {
    return this.no;
  }

  /**
   * 課題の配点を設定します。
   * 
   * @param allotment 課題の配点
   */
  public void setAllotment(int allotment) {
    this.allotment = allotment;
  }

  /**
   * 課題の配点を取得します。 　*
   * 
   * @return 課題の配点
   */
  public int getAllotment() {
    return this.allotment;
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
   * lessonIdを取得します。
   * 
   * @return lessonId
   */
  public int getLessonId() {
    return this.lessonId;
  }

  /**
   * lessonIdを設定します。
   * 
   * @param lessonId lessonId
   */
  public void setLessonId(int lessonId) {
    this.lessonId = lessonId;
  }

  /**
   * noを取得します。
   * 
   * @return no
   */
  public String getNo() {
    return this.no;
  }

  /**
   * titleを取得します。
   * 
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

}
