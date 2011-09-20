package org.mklab.taskit.shared;

import java.util.Date;

import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.AttendanceLocator;
import org.mklab.taskit.shared.AttendanceType;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(value = Attendance.class, locator = AttendanceLocator.class)
@SuppressWarnings("javadoc")
public interface AttendanceProxy extends EntityProxy {

  String getAccountId();

  Integer getLectureId();

  AttendanceType getType();

  Date getDate();

}
