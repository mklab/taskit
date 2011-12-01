/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.AttendanceLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * @see Attendance
 * @author Yuhi Ishikura
 */
@ProxyFor(value = Attendance.class, locator = AttendanceLocator.class)
@SuppressWarnings("javadoc")
public interface AttendanceProxy extends EntityProxy {
  
  Integer getId();

  AccountProxy getAttender();

  void setAttender(AccountProxy account);

  LectureProxy getLecture();

  void setLecture(LectureProxy lecture);

  AttendanceType getType();

  void setType(AttendanceType type);

  Date getDate();

}
