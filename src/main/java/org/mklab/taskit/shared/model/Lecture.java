package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * 講義を表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
@Entity(name = "LECTURE")
public final class Lecture extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = 7240243621664480506L;
  /** 管理用IDです。 */
  @Id
  @GeneratedValue
  private int lectureId = 0;
  /** 講義の日付です。 */
  private long time;
  /** 講義タイトルです。 */
  private String title;

  /**
   * {@link Lecture}オブジェクトを構築します。
   * 
   * @param title タイトル
   * @param date 講義日
   */
  public Lecture(String title, long date) {
    this.title = title;
    this.time = date;
  }

  /**
   * {@link Lecture}オブジェクトを構築します。
   */
  public Lecture() {
    // do nothing
  }

  /**
   * idを取得します。
   * 
   * @return id
   */
  public int getLectureId() {
    return this.lectureId;
  }

  /**
   * idを設定します。
   * 
   * @param id id
   */
  public void setLectureId(int id) {
    this.lectureId = id;
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
   * titleを取得します。
   * 
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * titleを設定します。
   * 
   * @param title title
   */
  public void setTitle(String title) {
    this.title = title;
  }

}
