/**
 * 
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
