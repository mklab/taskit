/**
 * 
 */
package org.mklab.taskit.server;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.mklab.taskit.shared.model.Lecture;


/**
 * @author teshima
 * @version $Revision$, 2011/07/20
 */
public class LectureMapper {

  public void map(Lecture lecture) {
    Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    mapper.map(lecture, Lecture.class);
    
  }
 
}
