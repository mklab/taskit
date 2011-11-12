/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.server.domain.ReportLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * @see Report
 * @author ishikura
 */
@ProxyFor(value = Report.class, locator = ReportLocator.class)
@SuppressWarnings("javadoc")
public interface ReportProxy extends EntityProxy {

  LectureProxy getLecture();

  void setLecture(LectureProxy lecture);

  Date getPeriod();

  void setPeriod(Date period);

  String getTitle();

  void setTitle(String title);

  String getDescription();

  void setDescription(String description);

  int getPoint();

  void setPoint(int point);

}
