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
package org.mklab.taskit.server.io;

/**
 * @author ishikura
 * @param <T> 分割するオブジェクトの型
 */
interface TableSplitter<T> {

  /**
   * {@link #split(Object)}で分割された各トークンの説明を取得します。
   * 
   * @return 列の説明
   */
  String[] getDescription();

  /**
   * オブジェクトをトークンに分割します。
   * 
   * @param object オブジェクト
   * @return 分割したトークン
   */
  String[] split(T object);

  /**
   * 分割されたトークンをマージしオブジェクトに戻します。
   * 
   * @param tokens トークン
   * @return オブジェクト
   */
  T merge(String[] tokens);

}
