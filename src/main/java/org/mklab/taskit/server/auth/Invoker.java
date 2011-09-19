package org.mklab.taskit.server.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mklab.taskit.shared.model.UserType;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Invoker {

  /** 実行可能なユーザーの配列です。 */
  UserType[] value();

}
