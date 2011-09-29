package org.mklab.taskit.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/15
 */
@SuppressWarnings("javadoc")
public interface TaskitRequestFactory extends RequestFactory {

  AccountRequest accountRequest();

  UserRequest userRequest();

  SubmissionRequest submissionRequest();

  AttendanceRequest attendanceRequest();

  HelpCallRequest helpCallRequest();

  LectureRequest lectureRequest();

  ReportRequest reportRequest();

}