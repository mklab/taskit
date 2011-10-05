/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentRecord;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 */
@ProxyFor(value = StudentRecord.class)
public interface StudentRecordProxy extends ValueProxy {

  List<SubmissionProxy> getSubmissions();

}
