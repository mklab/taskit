/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
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
  private String reportId;
  /** 課題の番号 */
  private String no;
  /** 課題のタイトル */
  private String title;
  /** 課題の詳細 */
  private String detail;
  /** 課題の配点 */
  private String allotment;

  /**
   * {@link Report}オブジェクトを構築します。
   * 
   * @param id 課題ID
   * @param no 課題の番号
   * @param title 課題の名前
   * @param detail 課題の詳細
   * @param allotment 課題の配点
   */
  public Report(String id, String no, String title, String detail, String allotment) {
    super();
    this.setId(id);
    this.setNo(no);
    this.setTitle(title);
    this.setDetail(detail);
    this.setAllotment(allotment);
  }

  /**
   * 課題IDを設定します。
   * 
   * @param id 課題ID
   */
  public void setId(String id) {
    this.reportId = id;
  }

  /**
   * 課題IDを取得します。
   * 
   * @return 課題ID
   */
  public String getId() {
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
  public void setAllotment(String allotment) {
    this.allotment = allotment;
  }

  /**
   * 課題の配点を取得します。 　*
   * 
   * @return 課題の配点
   */
  public String getAllotment() {
    return this.allotment;
  }

}
