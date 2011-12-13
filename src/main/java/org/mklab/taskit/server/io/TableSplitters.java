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
package org.mklab.taskit.server.io;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.User;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.UserType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ishikura
 */
@SuppressWarnings("nls")
class TableSplitters {

  static final Map<String, TableSplitter<?>> splitterMap = new HashMap<String, TableSplitter<?>>();

  static final String NULL = "<<NULL>>";

  static {
    registerSplitter("Account", new AccountSplitter());
    registerSplitter("User", new UserSplitter());
    registerSplitter("Lecture", new LectureSplitter());
    registerSplitter("Report", new ReportSplitter());
    registerSplitter("Submission", new SubmissionSplitter());
    registerSplitter("Attendance", new AttendanceSplitter());
  }

  static <T> void registerSplitter(String name, TableSplitter<T> splitter) {
    splitterMap.put(name, splitter);
  }

  static TableSplitter<?> getSplitter(String name) {
    return splitterMap.get(name);
  }

  static String[] toStringArray(Object... array) {
    String[] s = new String[array.length];
    for (int i = 0; i < s.length; i++) {
      Object elm = array[i];
      if (elm == null) {
        s[i] = NULL;
      } else if (elm instanceof Date) {
        s[i] = String.valueOf(((Date)elm).getTime());
      } else {
        s[i] = String.valueOf(array[i]);
      }
    }
    return s;
  }

  static void processNull(String[] tokens) {
    for (int i = 0; i < tokens.length; i++) {
      if (NULL.equals(tokens[i])) {
        tokens[i] = null;
      }
    }
  }

  static class AccountSplitter implements TableSplitter<Account> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"accountId", "password"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] split(Account object) {
      return toStringArray(object.getId(), object.getHashedPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account merge(String[] tokens) {
      processNull(tokens);
      final String id = tokens[0];
      final String hashedPassword = tokens[1];

      final Account account = new Account();
      account.setId(id);
      account.setHashedPassword(hashedPassword);

      return account;
    }

  }

  static class UserSplitter implements TableSplitter<User> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"userId", "accountId", "name", "type"};
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("boxing")
    @Override
    public String[] split(User object) {
      return toStringArray(object.getId(), object.getAccount().getId(), object.getName(), object.getType().ordinal());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User merge(String[] tokens) {
      processNull(tokens);
      final Integer id = Integer.valueOf(tokens[0]);
      final String accountId = tokens[1];
      final String name = tokens[2];
      final UserType type = UserType.values()[Integer.parseInt(tokens[3])];

      final Account account = Account.getAccountById(accountId);
      final User user = new User(account, type);
      user.setName(name);
      user.setId(id);
      return user;
    }

  }

  static class LectureSplitter implements TableSplitter<Lecture> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] split(Lecture lecture) {
      return toStringArray(lecture.getId(), lecture.getTitle(), lecture.getDescription(), lecture.getDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lecture merge(String[] tokens) {
      processNull(tokens);
      final Integer lectureId = Integer.valueOf(tokens[0]);
      final String title = tokens[1];
      final String description = tokens[2];
      final long date = Long.parseLong(tokens[3]);

      final Lecture lecture = new Lecture(new Date(date));
      lecture.setId(lectureId);
      lecture.setTitle(title);
      lecture.setDescription(description);
      return lecture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"lectureId", "title", "description", "date"};
    }
  }

  static class ReportSplitter implements TableSplitter<Report> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("boxing")
    @Override
    public String[] split(Report report) {
      return toStringArray(report.getId(), report.getTitle(), report.getDescription(), report.getPeriod(), report.getPoint(), report.getLecture().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report merge(String[] tokens) {
      processNull(tokens);
      final Integer reportId = Integer.valueOf(tokens[0]);
      final String title = tokens[1];
      final String description = tokens[2];
      final Date period = parseDate(tokens[3]);
      final int point = Integer.parseInt(tokens[4]);
      final int lectureId = Integer.parseInt(tokens[5]);

      final Lecture lecture = Lecture.getLectureById(lectureId);
      final Report report = new Report(title, point, lecture);
      report.setId(reportId);
      report.setDescription(description);
      report.setPeriod(period);
      return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"reportId", "title", "description", "period", "point", "lectureId"};
    }
  }

  static Date parseDate(String token) {
    if (token == null) return null;
    if (token.equals("null")) return null;

    long l = Long.parseLong(token);
    return new Date(l);
  }

  static class SubmissionSplitter implements TableSplitter<Submission> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"submissionId", "submitter", "report", "point", "date"};
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("boxing")
    @Override
    public String[] split(Submission object) {
      return toStringArray(object.getId(), object.getSubmitter().getId(), object.getReport().getId(), object.getPoint(), object.getDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submission merge(String[] tokens) {
      processNull(tokens);
      final Integer submissionId = Integer.valueOf(tokens[0]);
      final String submitterId = tokens[1];
      final int reportId = Integer.parseInt(tokens[2]);
      final int point = Integer.parseInt(tokens[3]);
      final long date = Long.parseLong(tokens[4]);

      final Account submitter = Account.getAccountById(submitterId);
      final Report report = Report.getReportById(reportId);
      final Submission submission = new Submission(point, submitter, report);
      submission.setId(submissionId);
      submission.setDate(new Date(date));

      return submission;
    }
  }

  static class AttendanceSplitter implements TableSplitter<Attendance> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDescription() {
      return new String[] {"attendanceId", "attendeeId", "type", "date", "lectureId"};
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("boxing")
    @Override
    public String[] split(Attendance object) {
      return toStringArray(object.getId(), object.getAttender().getId(), object.getType().ordinal(), object.getDate(), object.getLecture().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attendance merge(String[] tokens) {
      processNull(tokens);
      final Integer attendanceId = Integer.valueOf(tokens[0]);
      final String attendeeId = tokens[1];
      final int typeOrdinal = Integer.parseInt(tokens[2]);
      final long date = Long.parseLong(tokens[3]);
      final int lectureId = Integer.parseInt(tokens[4]);

      final Account attendee = Account.getAccountById(attendeeId);
      final AttendanceType type = AttendanceType.values()[typeOrdinal];
      final Lecture lecture = Lecture.getLectureById(lectureId);

      final Attendance attendance = new Attendance(type, attendee, lecture);
      attendance.setId(attendanceId);
      attendance.setLecture(lecture);
      attendance.setDate(new Date(date));
      return attendance;
    }

  }
}
