package org.mklab.taskit.server.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mklab.taskit.shared.UserType;


/**
 * リクエストを実行できるかどうかに関する情報を定義したアノテーションです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, 2011/09/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Invoker {

  /**
   * 実行可能なユーザーの配列です。
   * <p>
   * {@link #entrance()}が指定された場合には無視されます。
   */
  UserType[] value() default {};

  /** ユーザーの受け入れ判定に利用するクラスです。 */
  Class<? extends InvocationEntrance> entrance() default InvocationEntrance.class;

}
