package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Comment;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@ProxyFor(Comment.class)
public interface CommentProxy extends ValueProxy {
  
  String getComment();

  String getSecretComment();

}
