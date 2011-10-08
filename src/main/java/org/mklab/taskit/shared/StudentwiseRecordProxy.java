/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentwiseRecord;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 */
@ProxyFor(value = StudentwiseRecord.class)
@SuppressWarnings("javadoc")
public interface StudentwiseRecordProxy extends ValueProxy {

  UserProxy getStudent();

  List<SubmissionProxy> getSubmissions();

  List<AttendanceProxy> getAttendances();

}
