/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.TaskitRequestFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * テスト用の講義データを表すクラスです。
 * 
 * @author ishikura
 */
@SuppressWarnings("all")
public class Lectures {

  public LectureProxy lecture1;
  public ReportProxy lecture1_report1;
  public ReportProxy lecture1_report2;
  public ReportProxy lecture1_report3;

  public LectureProxy lecture2;
  public ReportProxy lecture2_report1;
  public ReportProxy lecture2_report2;

  public LectureProxy lecture3;
  public ReportProxy lecture3_report1;
  public ReportProxy lecture3_report2;
  public ReportProxy lecture3_report3;
  public ReportProxy lecture3_report4;

  public void initialize(TaskitRequestFactory requestFactory) {
    LectureRequest req = requestFactory.lectureRequest();
    this.lecture1 = createLecture(req, "1");
    this.lecture1_report1 = createReport(req, "1-1", this.lecture1);
    this.lecture1_report2 = createReport(req, "1-2", this.lecture1);
    this.lecture1_report3 = createReport(req, "1-3", this.lecture1);
    this.lecture1.setReports(Arrays.asList(this.lecture1_report1, this.lecture1_report2, this.lecture1_report3));

    this.lecture2 = createLecture(req, "2");
    this.lecture2_report1 = createReport(req, "2-1", this.lecture2);
    this.lecture2_report2 = createReport(req, "2-2", this.lecture2);
    this.lecture2.setReports(Arrays.asList(this.lecture2_report1, this.lecture2_report2));

    this.lecture3 = createLecture(req, "3");
    this.lecture3_report1 = createReport(req, "3-1", this.lecture3);
    this.lecture3_report2 = createReport(req, "3-2", this.lecture3);
    this.lecture3_report3 = createReport(req, "3-3", this.lecture3);
    this.lecture3_report4 = createReport(req, "3-4", this.lecture3);
    this.lecture3.setReports(Arrays.asList(this.lecture3_report1, this.lecture3_report2, this.lecture3_report3, this.lecture3_report4));

    req.persist().using(this.lecture1);
    req.persist().using(this.lecture2);
    req.persist().using(this.lecture3);
    req.fire(new Receiver<Void>() {

      @Override
      public void onSuccess(Void response) {
        // do nothing
      }

    });
  }

  private LectureProxy createLecture(RequestContext req, String title) {
    LectureProxy l = req.create(LectureProxy.class);
    l.setDate(new Date(System.currentTimeMillis() + (int)(Math.random() * TimeUnit.DAYS.toMillis(100))));
    l.setTitle(title);
    return l;
  }

  private ReportProxy createReport(RequestContext req, String title, LectureProxy lecture) {
    ReportProxy r = req.create(ReportProxy.class);
    r.setLecture(lecture);
    r.setTitle(title);
    return r;
  }

}
