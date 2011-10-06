/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.LecturewiseStudentRecord;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author yuhi
 */
@ProxyFor(value = LecturewiseStudentRecord.class)
@SuppressWarnings("javadoc")
public interface LecturewiseStudentRecordProxy extends ValueProxy {

  LectureProxy getLecture();

  AttendanceProxy getAttendance();

  List<SubmissionProxy> getSubmissions();
}
