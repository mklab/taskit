/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Report;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 */
@Service(Report.class)
@SuppressWarnings("javadoc")
public interface ReportRequest extends RequestContext {

  Request<List<ReportProxy>> getAllReports();

  InstanceRequest<ReportProxy, Void> persist();

  InstanceRequest<ReportProxy, Void> update();

}
