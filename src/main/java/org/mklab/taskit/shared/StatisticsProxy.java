/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Statistics;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author Yuhi Ishikura
 */
@ProxyFor(Statistics.class)
@SuppressWarnings("javadoc")
public interface StatisticsProxy extends ValueProxy {

  int getStudentCount();

  int getMaximumScore();

  double getAverage();

  double getStandardDeviation();

}
