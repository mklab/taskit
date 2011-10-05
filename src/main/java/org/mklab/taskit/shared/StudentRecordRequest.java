/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentRecords;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 */
@Service(value = StudentRecords.class)
@SuppressWarnings("javadoc")
public interface StudentRecordRequest extends RequestContext {

  Request<StudentRecordsProxy> getRecordByAccountId(String id);

  Request<StudentRecordsProxy> getAllRecords();

}
