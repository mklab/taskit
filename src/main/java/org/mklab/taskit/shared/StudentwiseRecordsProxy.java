/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.StudentwiseRecords;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 */
@SuppressWarnings("javadoc")
@ProxyFor(value = StudentwiseRecords.class)
public interface StudentwiseRecordsProxy extends ValueProxy {

  List<StudentwiseRecordProxy> getRecords();

  List<LectureProxy> getLectures();

}
