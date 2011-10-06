/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.LecturewiseStudentRecordsProxy;
import org.mklab.taskit.shared.SubmissionRequest;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author yuhi
 */
public class StudentScoreRequestTest extends DomainTest {

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

    getRequestFactory().studentRecordRequest().getLecturewiseRecordsByAccountId(STUDENT_PROXY.getAccount().getId()).fire(new Receiver<LecturewiseStudentRecordsProxy>() {

      @Override
      public void onSuccess(LecturewiseStudentRecordsProxy response) {

      }
    });
  }
}
