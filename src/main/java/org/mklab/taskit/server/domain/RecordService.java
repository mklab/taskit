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

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.List;


/**
 * 学生の成績に関するサービスを提供するクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class RecordService {

  private static StatisticsCollector statisticsCollector = new StatisticsCollector();

  static {
    statisticsCollector.start();
  }

  /**
   * 与えられたアカウントIDの生徒の成績統計を更新します。
   * 
   * @param accountId アカウントID
   */
  static void recomputeScore(String accountId) {
    statisticsCollector.recompute(accountId);
  }

  /**
   * 現時点での成績情報を取得します。
   * 
   * @return 現時点での成績情報
   */
  @Invoker({UserType.STUDENT})
  public static Record getMyRecord() {
    return statisticsCollector.getRecord(ServiceUtil.getLoginUser().getAccount().getId());
  }

  /**
   * 全生徒の現時点での成績情報を取得します。
   * 
   * @return 現時点での成績情報
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static List<Record> getAllRecords() {
    return statisticsCollector.getAllRecords();
  }

  static Record getRecord(String id) {
    return statisticsCollector.getRecord(id);
  }

}
