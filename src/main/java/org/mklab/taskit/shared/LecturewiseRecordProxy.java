/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.LecturewiseRecord;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author yuhi
 */
@ProxyFor(value = LecturewiseRecord.class)
@SuppressWarnings("javadoc")
public interface LecturewiseRecordProxy extends ValueProxy {

  LectureProxy getLecture();

  AttendanceProxy getAttendance();

  List<SubmissionProxy> getSubmissions();
}
