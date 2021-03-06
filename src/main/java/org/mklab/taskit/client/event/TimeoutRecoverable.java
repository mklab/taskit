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
package org.mklab.taskit.client.event;

/**
 * タイムアウトが発生した時に復帰する機能を提供するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface TimeoutRecoverable {

  /**
   * クライアントサイドでイベント待機時にタイムアウトが発生したときに呼び出されます。
   * 
   * @throws TimeoutRecoveryFailureException 復帰できなかった場合
   */
  void recoverFromTimeout() throws TimeoutRecoveryFailureException;

}
