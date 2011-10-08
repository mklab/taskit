/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.*;

import org.mklab.taskit.shared.LecturewiseRecordsProxy;
import org.mklab.taskit.shared.SubmissionRequest;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author yuhi
 */
public class StudentScoreRequestTest extends DomainTest {

  /**
   * 学生一人の、講義別成績取得のテストを行います。
   */
  @Test
  public void testGetLecturewiseRecordsByAccountId() {
    loginAsTeacher();
    Lectures lectures = new Lectures();
    lectures.initialize(getRequestFactory());

    loginAsTA();
    final SubmissionRequest req = getRequestFactory().submissionRequest();
    req.submit(TA_PROXY.getAccount(), lectures.lecture1_report1, 10);
    req.submit(TA_PROXY.getAccount(), lectures.lecture1_report2, 10);
    req.submit(TA_PROXY.getAccount(), lectures.lecture1_report3, 10);
    req.submit(TA_PROXY.getAccount(), lectures.lecture2_report1, 10);
    req.submit(TA_PROXY.getAccount(), lectures.lecture2_report2, 10);
    req.fire();

    getRequestFactory().studentRecordRequest().getLecturewiseRecordsByAccountId(STUDENT_PROXY.getAccount().getId()).with("records.submissions", "records.attendance") //$NON-NLS-1$ //$NON-NLS-2$
        .fire(new Receiver<LecturewiseRecordsProxy>() {

          @Override
          public void onSuccess(LecturewiseRecordsProxy response) {
            assertEquals(response.getRecords().get(0).getSubmissions().size(), 3);
            assertEquals(response.getRecords().get(1).getSubmissions().size(), 2);
          }
        });
  }

}
