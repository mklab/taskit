/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.RecordService;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author Yuhi Ishikura
 */
@Service(value = RecordService.class)
@SuppressWarnings("javadoc")
public interface RecordRequest extends RequestContext {

  Request<RecordProxy> getMyRecord();

  Request<List<RecordProxy>> getAllRecords();

}
