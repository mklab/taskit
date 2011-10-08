/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.LecturewiseRecords;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author yuhi
 */
@ProxyFor(value = LecturewiseRecords.class)
@SuppressWarnings("javadoc")
public interface LecturewiseRecordsProxy extends ValueProxy {

  UserProxy getStudent();

  List<LecturewiseRecordProxy> getRecords();

}
