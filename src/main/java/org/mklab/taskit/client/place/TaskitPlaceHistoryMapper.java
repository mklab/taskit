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
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
@WithTokenizers({Login.Tokenizer.class, StudentList.Tokenizer.class, AttendanceList.Tokenizer.class, Student.Tokenizer.class, Profile.Tokenizer.class, HelpCallList.Tokenizer.class,
    Admin.Tokenizer.class, LectureEdit.Tokenizer.class, ReportEdit.Tokenizer.class, CheckInList.Tokenizer.class, UserEdit.Tokenizer.class})
public interface TaskitPlaceHistoryMapper extends PlaceHistoryMapper {
  // empty
}
