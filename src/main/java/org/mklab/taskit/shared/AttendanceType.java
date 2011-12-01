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

/**
 * @author ishikura
 * @version $Revision$, 2011/09/20
 */
public enum AttendanceType {

  /** 正常に出席したことを表す定数です。 */
  PRESENT,
  /** 欠席したことを表す定数です。 */
  ABSENT,
  /** 遅刻したことを表す定数です。 */
  LATE,
  /** 病欠を表す定数です。 */
  ILLNESS

}
