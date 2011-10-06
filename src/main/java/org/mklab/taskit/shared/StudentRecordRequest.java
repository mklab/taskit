/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentRecordService;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 */
@Service(value = StudentRecordService.class)
@SuppressWarnings("javadoc")
public interface StudentRecordRequest extends RequestContext {

  Request<StudentRecordsProxy> getRecordByAccountId(String accountId);

  Request<StudentRecordsProxy> getAllRecords();

  Request<LecturewiseStudentRecordsProxy> getLecturewiseRecordsByAccountId(String accountId);

}
