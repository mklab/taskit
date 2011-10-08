/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.ReportRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class ReportRequestTest extends DomainTest {

  /**
   * {@link ReportRequest#persist()}のテストを行います。
   */
  @Test
  public void testPersist() {
    loginAsTeacher();

    createLectureAndReport();
    getReports();
    edit();
  }

  @SuppressWarnings("nls")
  private static void createLectureAndReport() {
    LectureRequest req = getRequestFactory().lectureRequest();
    final LectureProxy lecture = req.create(LectureProxy.class);
    lecture.setDate(new Date());
    final ReportProxy report = req.create(ReportProxy.class);
    report.setTitle("a");
    report.setLecture(lecture);
    final ReportProxy report2 = req.create(ReportProxy.class);
    report2.setTitle("b");
    report2.setLecture(lecture);
    lecture.setReports(Arrays.asList(report, report2));

    req.persist().using(lecture).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void arg0) {
        // do nothing
      }

    });
  }

  @SuppressWarnings("nls")
  private static void getReports() {
    ReportRequest req = getRequestFactory().reportRequest();
    req.getAllReports().with("lecture").fire(new Receiver<List<ReportProxy>>() {

      @Override
      public void onSuccess(List<ReportProxy> response) {
        assertEquals(2, response.size());

        final ReportProxy r1 = response.get(0);
        final ReportProxy r2 = response.get(1);
        assertTrue((r1.getTitle().equals("a") && r2.getTitle().equals("b")) || (r1.getTitle().equals("b") && r2.getTitle().equals("a")));
        assertNotNull(r1.getLecture());
        assertNotNull(r2.getLecture());
        assertTrue(r1.getLecture() == r2.getLecture());

        /*
         * カスケード先のカスケードまでは見てくれないみたい？
         */
        assertNull(r1.getLecture().getReports());
      }

    });
  }

  @SuppressWarnings("nls")
  private static void edit() {
    ReportRequest req = getRequestFactory().reportRequest();
    req.getAllReports().fire(new Receiver<List<ReportProxy>>() {

      @Override
      public void onSuccess(List<ReportProxy> response) {
        ReportRequest requestForUpdate = getRequestFactory().reportRequest();
        ReportProxy editableReport = requestForUpdate.edit(response.get(0));
        editableReport.setDescription("hoge");
        requestForUpdate.update().using(editableReport).fire();
      }

    });

    req = getRequestFactory().reportRequest();
    req.getAllReports().fire(new Receiver<List<ReportProxy>>() {

      @Override
      public void onSuccess(List<ReportProxy> response) {
        assertEquals("hoge", response.get(0).getDescription());
      }

    });
  }
}
