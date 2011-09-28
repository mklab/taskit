package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.AttendanceLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(value = Attendance.class, locator = AttendanceLocator.class)
@SuppressWarnings("javadoc")
public interface AttendanceProxy extends EntityProxy {

  AccountProxy getAttender();

  void setAttender(AccountProxy account);

  LectureProxy getLecture();

  void setLecture(LectureProxy lecture);

  AttendanceType getType();

  void setType(AttendanceType type);

  Date getDate();

}
