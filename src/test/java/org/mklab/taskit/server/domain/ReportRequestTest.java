/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.ReportRequest;

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

    final ReportProxy report = req.create(ReportProxy.class);
    report.setTitle("a");
    req.persist().using(report).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void arg0) {
        // TODO Auto-generated method stub

      }

    });
    System.out.println();
  }

}
