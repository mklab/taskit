/**
 * 
 */
package org.mklab.taskit.client.model;

import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.SubmissionRequest;
import org.mklab.taskit.shared.TaskitRequestFactory;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * 成績データの非同期取得を行うクラスです。
 * 
 * @author ishikura
 */
public class StudentScoreQuery {

  private TaskitRequestFactory requestFactory;
  private List<LectureProxy> lecturesCache;

  static class Proxies {

    List<LectureProxy> lectures;
    List<AttendanceProxy> attendances;
    List<SubmissionProxy> submissions;

  }

  /**
   * {@link StudentScoreQuery}オブジェクトを構築します。
   * 
   * @param requestFactory リクエストファクトリ
   */
  public StudentScoreQuery(TaskitRequestFactory requestFactory) {
    if (requestFactory == null) throw new NullPointerException();
    this.requestFactory = requestFactory;
  }

  /**
   * 成績データを取得します。
   * 
   * @param accountId 生徒アカウントID
   * @param handler 取得結果を処理するハンドラ
   */
  public void query(String accountId, Handler handler) {
    final Proxies p = new Proxies();
    p.lectures = this.lecturesCache;

    fetchLectures(p, handler);
    fetchAttendances(accountId, p, handler);
    fetchSubmissions(accountId, p, handler);
  }

  /**
   * 成績データを取得します。
   * 
   * @param handler 取得結果を処理するハンドラ
   */
  public void query(Handler handler) {
    final Proxies p = new Proxies();
    p.lectures = this.lecturesCache;

    fetchLectures(p, handler);
    fetchMyAttendances(p, handler);
    fetchMySubmissions(p, handler);
  }

  private void fetchLectures(final Proxies p, final Handler handler) {
    final LectureRequest lectureRequest = this.requestFactory.lectureRequest();
    lectureRequest.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

          @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
          @Override
          public void onSuccess(List<LectureProxy> response) {
            p.lectures = response;
            lecturesCache = response;
            handleResultIfPossible(p, handler);
          }

        });
  }

  private void fetchAttendances(final String accountId, final Proxies p, final Handler handler) {
    final AttendanceRequest attendanceRequest = this.requestFactory.attendanceRequest();
    attendanceRequest.getAttendancesByAccountId(accountId).with("lecture").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            p.attendances = response;
            handleResultIfPossible(p, handler);
          }

        });
  }

  private void fetchMyAttendances(final Proxies p, final Handler handler) {
    final AttendanceRequest attendanceRequest = this.requestFactory.attendanceRequest();
    attendanceRequest.getMyAttendances().with("lecture").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<AttendanceProxy> response) {
            p.attendances = response;
            handleResultIfPossible(p, handler);
          }

        });
  }

  private void fetchSubmissions(final String accountId, final Proxies p, final Handler handler) {
    final SubmissionRequest submissionRequest = this.requestFactory.submissionRequest();
    submissionRequest.getSubmissionsByAccountId(accountId).with("report.lecture").fire(new Receiver<List<SubmissionProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<SubmissionProxy> response) {
            p.submissions = response;
            handleResultIfPossible(p, handler);
          }

        });
  }

  private void fetchMySubmissions(final Proxies p, final Handler handler) {
    final SubmissionRequest submissionRequest = this.requestFactory.submissionRequest();
    submissionRequest.getMySubmissions().with("report.lecture").fire(new Receiver<List<SubmissionProxy>>() { //$NON-NLS-1$

          @SuppressWarnings("synthetic-access")
          @Override
          public void onSuccess(List<SubmissionProxy> response) {
            p.submissions = response;
            handleResultIfPossible(p, handler);
          }

        });
  }

  private static void handleResultIfPossible(Proxies p, Handler handler) {
    if (p.lectures != null && p.attendances != null && p.submissions != null) {
      final StudentScoreModel model = new StudentScoreModel(p.lectures, p.attendances, p.submissions);
      handler.handleResult(model);
    }
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
    void handleResult(StudentScoreModel model);
  }

}
