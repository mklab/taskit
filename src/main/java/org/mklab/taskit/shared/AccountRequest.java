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
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Account;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see Account
 * @author ishikura
 * @version $Revision$, 2011/09/18
 */
@Service(value = Account.class)
public interface AccountRequest extends RequestContext {

  /**
   * 新規アカウントを作成します。
   * 
   * @param id アカウントID
   * @param password パスワード
   * @param userType ユーザー種別
   * @return リクエスト
   */
  Request<Void> registerNewAccount(String id, String password, UserType userType);

  /**
   * 与えられたIDのアカウントの登録を抹消します。
   * 
   * @param id アカウントID
   * @return リクエスト
   */
  Request<Void> unregisterAccount(String id);

  /**
   * IDからアカウントを取得します。
   * 
   * @param id アカウントID
   * @return アカウント。存在しなければnull
   */
  Request<AccountProxy> getAccountById(String id);

  /**
   * ログインを行います。
   * 
   * @param id アカウントID
   * @param password パスワード
   * @return ログインユーザーデータ
   */
  Request<UserProxy> login(String id, String password);

  /**
   * ログアウトを行います。
   * 
   * @return リクエスト
   */
  Request<Void> logout();

  /**
   * パスワードを変更します(管理者用)。
   * 
   * @param account 変更するアカウント
   * @param newPassword 新しいパスワード
   * @return リクエスト
   */
  Request<Void> changePassword(AccountProxy account, String newPassword);

  /**
   * 自分のパスワードを変更します。
   * 
   * @param oldPassword 古いパスワード
   * @param newPassword 新しいパスワード
   * @return リクエスト
   */
  Request<Void> changeMyPassword(String oldPassword, String newPassword);

}
