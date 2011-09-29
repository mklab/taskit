package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Attendance;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(Attendance.class)
@SuppressWarnings("javadoc")
public interface AttendanceRequest extends RequestContext {

  InstanceRequest<AttendanceProxy, Void> persist();

  Request<List<AttendanceProxy>> getAllAttendancesByAccountId(String accountId);

  Request<Void> attend(AccountProxy attender, LectureProxy lecture, AttendanceType type);

}
