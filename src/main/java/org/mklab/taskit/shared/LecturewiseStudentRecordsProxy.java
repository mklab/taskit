/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.LecturewiseStudentRecords;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author yuhi
 */
@ProxyFor(value = LecturewiseStudentRecords.class)
@SuppressWarnings("javadoc")
public interface LecturewiseStudentRecordsProxy extends ValueProxy {

  UserProxy getStudent();

  List<LecturewiseStudentRecordProxy> getRecords();

}
