/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Record;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author Yuhi Ishikura
 */
@ProxyFor(Record.class)
@SuppressWarnings("javadoc")
public interface RecordProxy extends ValueProxy {

  String getAccountId();

  StatisticsProxy getStatistics();

  int getRank();

  double getScore();

  double getDeviation();

}
