/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.ReportRequest;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author ishikura
 */
public class ReportRequestTest extends DomainTest {

  @Test
  public void testPersist() {
    loginAsTeacher();

    ReportRequest req = getRequestFactory().reportRequest();

    final LectureProxy lecture = req.create(LectureProxy.class);
    lecture.setDate(new Date());
    final ReportProxy report = req.create(ReportProxy.class);
    report.setTitle("a");
    report.setLecture(lecture);
    lecture.setReports(Arrays.asList(report));

    
    req.persist().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void arg0) {

      }

    });
    System.out.println();
  }

}
