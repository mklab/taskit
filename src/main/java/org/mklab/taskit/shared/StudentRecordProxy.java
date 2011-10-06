/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentRecord;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 */
@ProxyFor(value = StudentRecord.class)
@SuppressWarnings("javadoc")
public interface StudentRecordProxy extends ValueProxy {

  UserProxy getStudent();

  List<SubmissionProxy> getSubmissions();

  List<AttendanceProxy> getAttendances();

}