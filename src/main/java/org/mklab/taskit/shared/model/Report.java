/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * @author teshima
 * @version $Revision$, Jan 25, 2011
 */
@Entity(name = "REPORT")
public final class Report extends LightEntity {

  /** */
  private static final long serialVersionUID = 1L;

  /** 課題のID */
  private String id;
  /** 課題の名前 */
  private String name;
  /** 課題の詳細 */
  private String detail;
  /** 課題が出された日付 */
  private String date;
  /** 課題のレベル（番号） */
  private String level;
  /** 課題の配点 */
  private String allotment;
  /**
   * {@link Report}オブジェクトを構築します。
   * 
   * @param id 課題ID
   * @param name 課題の名前
   * @param detail 課題の詳細
   * @param date 課題が出された日付
   * @param level 課題のレベル（番号）
   * @param allotment 課題の配点
   */
  public Report(String id, String name, String detail, String date, String level, String allotment) {
    super();
    this.setId(id);
    this.setName(name);
    this.setDetail(detail);
    this.setDate(date);
    this.setLevel(level);
    this.setAllotment(allotment);
  }
  /**
   * 課題IDを設定します。
   *
   * @param id 課題ID
   */
  public void setId(String id) {
    this.id = id;
  }
  /**
   * 課題IDを取得します。
  　*
   * @return 課題ID
   */
  public String getId() {
    return this.id;
  }
  /**
   * 課題の名前を設定します。
   *
   * @param name 課題の名前
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * 課題の名前を取得します。
  　*
   * @return 課題の名前
   */
  public String getName() {
    return this.name;
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
   * 課題の詳細説明を取得します。
  　*
   * @return 課題の詳細説明
   */
  public String getDetail() {
    return this.detail;
  }
  /**
   * 課題の出された日付を設定します。
   *
   * @param date 課題の出された日付
   */
  public void setDate(String date) {
    this.date = date;
  }
  /**
   * 課題の出された日付を取得します。
  　*
   * @return 課題の出された日付
   */
  public String getDate() {
    return this.date;
  }
  /**
   * 課題のレベル（番号）を設定します。
   *
   * @param level 課題のレベル（番号）
   */
  public void setLevel(String level) {
    this.level = level;
  }
  /**
   * 課題のレベル（番号）を取得します。
  　*
   * @return 課題のレベル（番号）
   */
  public String getLevel() {
    return this.level;
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
   * 課題の配点を取得します。
  　*
   * @return 課題の配点
   */
  public String getAllotment() {
    return this.allotment;
  }
  

}
