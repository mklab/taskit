package org.mklab.taskit.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Report extends AbstractEntity<Integer> {

  private Integer id;
  /** 提出期限です。 */
  private Date period;
  /** レポートのタイトルです。 */
  @NotNull
  private String title;
  /** レポートの詳細な説明です。 */
  private String description;
  /** レポートの最大点数です。 */
  private int point;

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(Integer id) {
    this.id = id;
  }

  Date getPeriod() {
    return this.period;
  }

  void setPeriod(Date period) {
    this.period = period;
  }

  String getTitle() {
    return this.title;
  }

  void setTitle(String title) {
    this.title = title;
  }

  String getDescription() {
    return this.description;
  }

  void setDescription(String description) {
    this.description = description;
  }

  int getPoint() {
    return this.point;
  }

  void setPoint(int point) {
    this.point = point;
  }

}
