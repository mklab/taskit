package org.mklab.taskit.shared;

import java.util.List;

import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.shared.AttendanceType;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(Attendance.class)
@SuppressWarnings("javadoc")
public interface AttendanceRequest extends RequestContext {

  Request<AttendanceProxy> mark(String accountId, Integer lectureId, AttendanceType type);

  Request<List<AttendanceProxy>> getAllAttendancesByAccountId(String accountId);

}
