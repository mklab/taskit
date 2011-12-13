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

import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.LectureLocator;

import java.util.Date;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


/**
 * @see Lecture
 * @author yuhi
 */
@ProxyFor(value = Lecture.class, locator = LectureLocator.class)
@SuppressWarnings("javadoc")
public interface LectureProxy extends EntityProxy {

  Integer getId();

  Date getDate();

  void setDate(Date date);

  String getTitle();

  void setTitle(String title);

  String getDescription();

  void setDescription(String description);

  List<ReportProxy> getReports();

  void setReports(List<ReportProxy> reports);

}
