/**
 * 
 */
package org.mklab.taskit.shared.validation;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public final class AccountValidator {

  /**
   * バリデーション結果を表す列挙型です。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 26, 2011
   */
  public static enum Result {

    /**
     * IDが短すぎることを表す定数です。
     */
    TOO_SHORT_ID,
    /**
     * パスワードが短すぎることを表す定数です。
     */
    TOO_SHORT_PASSWORD,
    /**
     * IDとパスワードが同一であることを表す定数です。
     */
    ID_EQUALS_TO_PASSWORD,
    /**
     * 問題がないことを表す定数です。
     */
    VALID;
  }

  /**
   * {@link AccountValidator}オブジェクトを構築します。
   */
  private AccountValidator() {
    // do nothing
  }

  /**
   * アカウントのチェックを行ないます。
   * 
   * @param id ID
   * @param password パスワード
   * @return 検証結果
   */
  public static Result validate(String id, String password) {
    if (id.length() == 0) return Result.TOO_SHORT_ID;
    if (password.length() == 0) return Result.TOO_SHORT_PASSWORD;
    if (id.equals(password)) return Result.ID_EQUALS_TO_PASSWORD;
    return Result.VALID;
  }

}
