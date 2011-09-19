package org.mklab.taskit.server.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthenticationEntryPoint {
  // empty. this annotation works only as a marker.
}
