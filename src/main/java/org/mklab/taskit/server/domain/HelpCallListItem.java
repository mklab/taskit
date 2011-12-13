/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.server.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * 呼び出しリストで参照するリストアイテムを表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class HelpCallListItem {

  private HelpCall helpCall;
  private List<String> usersInCharge = new ArrayList<String>();

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
    if (helpCall == null) throw new NullPointerException();
    if (usersInCharge == null) throw new NullPointerException();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.helpCall == null) ? 0 : this.helpCall.hashCode());
    result = prime * result + ((this.usersInCharge == null) ? 0 : this.usersInCharge.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    HelpCallListItem other = (HelpCallListItem)obj;
    if (this.helpCall == null) {
      if (other.helpCall != null) return false;
    } else if (!this.helpCall.equals(other.helpCall)) return false;
    if (this.usersInCharge == null) {
      if (other.usersInCharge != null) return false;
    } else if (!this.usersInCharge.equals(other.usersInCharge)) return false;
    return true;
  }

}
