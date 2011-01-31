package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
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
  /** アカウントのIDです。 */
  @Id
  private String id;
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
   * @param id ID
   * @param password パスワード
   * @param accountType アカウントの種類
   */
  public Account(String id, String password, String accountType) {
    super();
    this.id = id;
    this.password = password;
    this.accountType = accountType;
  }

  /**
   * IDを取得します。
   * 
   * @return ID
   */
  public String getId() {
    return this.id;
  }

  /**
   * IDを設定します。
   * 
   * @param id ID
   */
  public void setId(String id) {
    this.id = id;
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
   * パスワードを取得します。
   * 
   * @return パスワード
   */
  public String getPassword() {
    return this.password;
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
