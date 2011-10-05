/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentRecords;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 */
@SuppressWarnings("javadoc")
@ProxyFor(value = StudentRecords.class)
public interface StudentRecordsProxy extends ValueProxy {

  List<StudentRecordProxy> getRecords();

  List<LectureProxy> getLectures();

}
