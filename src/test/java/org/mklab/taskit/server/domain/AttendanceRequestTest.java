package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.SubmissionRequest;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * {@link SubmissionRequest}のテストケースです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class AttendanceRequestTest extends DomainTest {

  /**
   * 提出が正常に行われるかどうかテストします。
   */
  @Test
  @SuppressWarnings("unused")
  public void testSubmit() {
    loginAsTA();

    AttendanceRequest req = getRequestFactory().attendanceRequest();
    final String id = "qeqrjzklf"; //$NON-NLS-1$
    final Integer lectureId = Integer.valueOf(1);

    req.mark(id, lectureId, AttendanceType.LATE).fire(new Receiver<AttendanceProxy>() {

      @Override
      public void onSuccess(AttendanceProxy response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        fail(error.getMessage());
      }

    });

    req = getRequestFactory().attendanceRequest();
    req.getAllAttendancesByAccountId(id).fire(new Receiver<List<AttendanceProxy>>() {

      @Override
      public void onSuccess(List<AttendanceProxy> response) {
        assertEquals(1, response.size());
        final AttendanceProxy s = response.get(0);
        assertEquals(id, s.getAccountId());
        assertEquals(lectureId, s.getLectureId());
      }

    });
  }

  /**
   * アカウントとレポートのIDの組み合わせが重複する提出がなされた場合に例外が発生するかどうかテストします。
   */
  @Test
  @SuppressWarnings({"unused", "nls"})
  public void testSubmitDuplicatePair() {
    loginAsTA();

    AttendanceRequest req = getRequestFactory().attendanceRequest();
    final String id = "asdfasfdasdf";
    final Integer lectureId = Integer.valueOf(1);
    req.mark(id, lectureId, AttendanceType.LATE).fire(new Receiver<AttendanceProxy>() {

      @Override
      public void onSuccess(AttendanceProxy response) {
        // do nothing
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        fail();
      }

    });
    req = getRequestFactory().attendanceRequest();
    req.mark(id, lectureId, AttendanceType.LATE).fire(new Receiver<AttendanceProxy>() {

      @Override
      public void onSuccess(AttendanceProxy response) {
        fail();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        // do nothing
      }

    });
  }
}
