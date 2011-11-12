/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Lecture;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see Lecture
 * @author ishikura
 */
@Service(value = Lecture.class)
@SuppressWarnings("javadoc")
public interface LectureRequest extends RequestContext {

  Request<List<LectureProxy>> getAllLectures();

  InstanceRequest<LectureProxy, Void> persist();

  InstanceRequest<LectureProxy, Void> update();

  InstanceRequest<LectureProxy, Void> updateOrCreate();

  InstanceRequest<LectureProxy, Void> delete();

}
