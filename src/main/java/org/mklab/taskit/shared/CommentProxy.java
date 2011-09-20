package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Comment;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(Comment.class)
@SuppressWarnings("javadoc")
public interface CommentProxy extends ValueProxy {

  String getComment();

  String getSecretComment();

}
