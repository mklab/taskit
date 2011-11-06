/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * 呼び出しリストで参照するリストアイテムを表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class HelpCallListItem implements ValueProxy {

  private HelpCall helpCall;
  private List<String> usersInCharge;

  /**
   * {@link HelpCallListItem}オブジェクトを構築します。
   */
  public HelpCallListItem() {
    // for framework
  }

  /**
   * {@link HelpCallListItem}オブジェクトを構築します。
   * 
   * @param helpCall 呼び出し情報
   * @param usersInCharge 呼び出し生徒に対応中の担当者
   */
  public HelpCallListItem(HelpCall helpCall, List<String> usersInCharge) {
    super();
    this.helpCall = helpCall;
    this.usersInCharge = usersInCharge;
  }

  /**
   * 呼び出し情報を取得します。
   * 
   * @return 呼び出し情報
   */
  public HelpCall getHelpCall() {
    return this.helpCall;
  }

  /**
   * 呼び出しに対応中の担当者を取得します。
   * 
   * @return usersInCharge 担当者のリスト
   */
  public List<String> getUsersInCharge() {
    return this.usersInCharge;
  }

}
