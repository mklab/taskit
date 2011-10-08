/**
 * 
 */
package org.mklab.taskit.client.model;

import org.mklab.taskit.shared.LecturewiseRecordsProxy;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * 成績データの非同期取得を行うクラスです。
 * 
 * @author ishikura
 */
public class StudentwiseRecordQuery {

  private TaskitRequestFactory requestFactory;
  @SuppressWarnings("nls")
  private static final String[] recordPropertyRefs = {"records.submissions.submitter", "records.submissions.report", "records.lecture.reports"};

  /**
   * {@link StudentwiseRecordQuery}オブジェクトを構築します。
   * 
   * @param requestFactory リクエストファクトリ
   */
  public StudentwiseRecordQuery(TaskitRequestFactory requestFactory) {
    if (requestFactory == null) throw new NullPointerException();
    this.requestFactory = requestFactory;
  }

  /**
   * 成績データを取得します。
   * 
   * @param accountId 生徒アカウントID
   * @param handler 取得結果を処理するハンドラ
   */
  public void query(String accountId, final Handler handler) {
    this.requestFactory.recordRequest().getLecturewiseRecordsByAccountId(accountId).with(recordPropertyRefs).fire(new Receiver<LecturewiseRecordsProxy>() {

      @Override
      public void onSuccess(LecturewiseRecordsProxy response) {
        final StudentwiseRecordModel records = new StudentwiseRecordModel(response);
        handler.handleResult(records);
      }

    });
  }

  /**
   * 成績データを取得します。
   * 
   * @param handler 取得結果を処理するハンドラ
   */
  public void query(final Handler handler) {
    this.requestFactory.recordRequest().getMyLecturewiseRecords().with(recordPropertyRefs).fire(new Receiver<LecturewiseRecordsProxy>() {

      @Override
      public void onSuccess(LecturewiseRecordsProxy response) {
        final StudentwiseRecordModel records = new StudentwiseRecordModel(response);
        handler.handleResult(records);
      }

    });
  }

  /**
   * 取得した成績データを処理するインターフェースです。
   * 
   * @author ishikura
   */
  public interface Handler {

    /**
     * 取得結果を処理します。
     * 
     * @param model 取得した成績データ
     */
    void handleResult(StudentwiseRecordModel model);
  }

}
