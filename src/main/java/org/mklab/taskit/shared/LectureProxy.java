/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.LectureLocator;

import java.util.Date;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * @author yuhi
 */
@ProxyFor(value = Lecture.class, locator = LectureLocator.class)
@SuppressWarnings("javadoc")
public interface LectureProxy extends EntityProxy {

  Date getDate();

  void setDate(Date date);

  String getTitle();

  void setTitle(String title);

  String getDescription();

  void setDescription(String description);

  List<ReportProxy> getReports();

  void setReports(List<ReportProxy> reports);

}
