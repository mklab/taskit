/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.LectureLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * @author yuhi
 */
@ProxyFor(value = Lecture.class, locator = LectureLocator.class)
@SuppressWarnings("javadoc")
public interface LectureProxy extends EntityProxy {

  Date getDate();

  String getTitle();

  String getDescription();

}
