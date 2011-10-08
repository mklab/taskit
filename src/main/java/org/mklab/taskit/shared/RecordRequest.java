/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.RecordService;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 */
@Service(value = RecordService.class)
@SuppressWarnings("javadoc")
public interface RecordRequest extends RequestContext {

  Request<LecturewiseRecordsProxy> getMyLecturewiseRecords();

  Request<StudentwiseRecordsProxy> getAllRecords();

  Request<LecturewiseRecordsProxy> getLecturewiseRecordsByAccountId(String accountId);

}
