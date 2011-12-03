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
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.AttendanceList;
import org.mklab.taskit.client.place.CheckInList;
import org.mklab.taskit.client.place.HelpCallList;
import org.mklab.taskit.client.place.LectureEdit;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.place.Profile;
import org.mklab.taskit.client.place.ReportEdit;
import org.mklab.taskit.client.place.Student;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.place.UserEdit;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;


/**
 * TASKitのアクティビティの{@link Place}からのマッピングを行うクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class TaskitActivityMapper implements ActivityMapper {

  private ClientFactory clientFactory;

  /**
   * {@link TaskitActivityMapper}オブジェクトを構築します。
   * 
   * @param clientFactory {@link ClientFactory}インスタンス
   */
  public TaskitActivityMapper(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Activity getActivity(final Place place) {
    if (place instanceof Login) return new LoginActivity(this.clientFactory);

    return getTaskitActivity(place);
  }

  private TaskitActivity getTaskitActivity(final Place place) {
    if (place instanceof StudentList) return new StudentListActivity(this.clientFactory);
    if (place instanceof AttendanceList) return new AttendanceListActivity(this.clientFactory);
    if (place instanceof Student) return new StudentActivity(this.clientFactory);
    if (place instanceof Profile) return new ProfileActivity(this.clientFactory);
    if (place instanceof HelpCallList) return new HelpCallListActivity(this.clientFactory);
    if (place instanceof LectureEdit) return new LectureEditActivity(this.clientFactory);
    if (place instanceof ReportEdit) return new ReportEditActivity(this.clientFactory);
    if (place instanceof Admin) return new AdminActivity(this.clientFactory);
    if (place instanceof CheckInList) return new CheckInListActivity(this.clientFactory);
    if (place instanceof UserEdit) return new UserEditActivity(this.clientFactory);

    return null;
  }

}
