/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 28, 2011
 */
@Entity(name = "STUDENT")
public class Student extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = 300937258727774557L;

  /** アカウントのIDです。 */
  @Id
  @GeneratedValue
  private int accountId;

  /** 学籍番号です。 */
  private String studentNo;

  /**
   * {@link Student}オブジェクトを構築します。
   * 
   * @param studentNo 学籍番号
   */
  public Student(String studentNo) {
    this.studentNo = studentNo;
  }

  /**
   * {@link Student}オブジェクトを構築します。
   * <p>
   * for gwt serialization.
   */
  Student() {
    // do nothing
  }

  /**
   * accountIdを取得します。
   * 
   * @return accountId
   */
  public int getAccountId() {
    return this.accountId;
  }

  /**
   * accountIdを設定します。
   * 
   * @param accountId accountId
   */
  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  /**
   * studentNoを取得します。
   * 
   * @return studentNo
   */
  public String getStudentNo() {
    return this.studentNo;
  }

  /**
   * studentNoを設定します。
   * 
   * @param studentNo studentNo
   */
  public void setStudentNo(String studentNo) {
    this.studentNo = studentNo;
  }

}
