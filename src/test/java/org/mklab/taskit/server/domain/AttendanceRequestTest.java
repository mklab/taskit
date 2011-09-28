package org.mklab.taskit.server.domain;

import static org.junit.Assert.*;

import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.AttendanceProxy;
import org.mklab.taskit.shared.AttendanceRequest;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.SubmissionRequest;

import java.util.List;

import org.junit.Test;

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
    // register lectures for test
    final Lectures l = new Lectures();
    {
      loginAsTeacher();
      l.initialize(getRequestFactory());
    }

    // mark as attended
    {
      loginAsTA();
      final AttendanceRequest req = getRequestFactory().attendanceRequest();

      getRequestFactory().accountRequest().getAccountById(STUDENT.getAccount().getId()).fire(new Receiver<AccountProxy>() {

        @Override
        public void onSuccess(AccountProxy response) {
          AttendanceProxy attendance = req.create(AttendanceProxy.class);
          attendance.setAttender(response);
          attendance.setLecture(l.lecture1);
          attendance.setType(AttendanceType.PRESENT);
          req.persist().using(attendance).fire(new Receiver<Void>() {

            @Override
            public void onSuccess(@SuppressWarnings("hiding") Void response) {
              // do nothing
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void onFailure(ServerFailure error) {
              System.err.println("Stack Trace: " + error.getStackTraceString()); //$NON-NLS-1$
              fail(error.getMessage());
            }

          });
        }
      });
    }

    //check if marked correctly
    {
      loginAsTA();
      final AttendanceRequest req = getRequestFactory().attendanceRequest();

      req.getAllAttendancesByAccountId(STUDENT.getAccount().getId()).with("lecture.reports", "attender").fire(new Receiver<List<AttendanceProxy>>() { //$NON-NLS-1$ //$NON-NLS-2$

            @Override
            public void onSuccess(List<AttendanceProxy> response) {
              assertEquals(1, response.size());
              AttendanceProxy a = response.get(0);
              assertEquals(AttendanceType.PRESENT, a.getType());
              assertNotNull(a.getLecture());
              assertNotNull(a.getLecture().getReports());
              assertNotNull(a.getAttender());
            }

          });
    }

  }

  //  /**
  //   * アカウントとレポートのIDの組み合わせが重複する提出がなされた場合に例外が発生するかどうかテストします。
  //   */
  //  @Test
  //  @SuppressWarnings({"unused", "nls"})
  //  public void testSubmitDuplicatePair() {
  //    loginAsTA();
  //
  //    AttendanceRequest req = getRequestFactory().attendanceRequest();
  //    final String id = "asdfasfdasdf";
  //    final Integer lectureId = Integer.valueOf(1);
  //    req.mark(id, lectureId, AttendanceType.LATE).fire(new Receiver<AttendanceProxy>() {
  //
  //      @Override
  //      public void onSuccess(AttendanceProxy response) {
  //        // do nothing
  //      }
  //
  //      /**
  //       * {@inheritDoc}
  //       */
  //      @Override
  //      public void onFailure(ServerFailure error) {
  //        fail();
  //      }
  //
  //    });
  //    req = getRequestFactory().attendanceRequest();
  //    req.mark(id, lectureId, AttendanceType.LATE).fire(new Receiver<AttendanceProxy>() {
  //
  //      @Override
  //      public void onSuccess(AttendanceProxy response) {
  //        fail();
  //      }
  //
  //      /**
  //       * {@inheritDoc}
  //       */
  //      @Override
  //      public void onFailure(ServerFailure error) {
  //        // do nothing
  //      }
  //
  //    });
  //  }
}
