package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * アカウントを表すクラスです。
 * 
 * @author teshima
 * @version $Revision$, 2011/01/18
 */
@Entity(name = "ACCOUNT")
public final class Account extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = -4244616815344670645L;
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int accountId = 0;
  /** ユーザー名です。 */
  private String userName;
  /** アカウントのパスワードです。 */
  private String password;
  /** アカウントの種類です。 */
  private String accountType;//

  /**
   * {@link Account}オブジェクトを構築します。
   * <p>
   * Serialization用。
   */
  public Account() {
    // do nothing
  }

  /**
   * Initialize the generated object of {@link Account}.
   * 
   * @param userName ID
   * @param password パスワード
   * @param accountType アカウントの種類
   */
  public Account(String userName, String password, String accountType) {
    super();
    this.userName = userName;
    this.password = password;
    this.accountType = accountType;
  }

  /**
   * IDを取得します。
   * 
   * @return ID
   */
  public int getAccountId() {
    return this.accountId;
  }

  /**
   * IDを設定します。
   * 
   * @param id ID
   */
  public void setAccountId(int id) {
    this.accountId = id;
  }

  /**
   * ユーザー名を取得します。
   * 
   * @return ユーザー名
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * ユーザー名を設定します。
   * 
   * @param userName ユーザー名
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * パスワードを取得します。
   * 
   * @return パスワード
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * パスワードを設定します。
   * 
   * @param password パスワード
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * アカウントの種類を取得します。
   * 
   * @return アカウントの種類
   */
  public String getAccountType() {
    return this.accountType;
  }

  /**
   * アカウントの種類を設定します。
   * 
   * @param accountType アカウントの種類
   */
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

}
