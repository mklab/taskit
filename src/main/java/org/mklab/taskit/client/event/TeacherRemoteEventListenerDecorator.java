/**
 * 
 */
package org.mklab.taskit.client.event;

/**
 * 先生用のリモートイベントリスナです。
 * 
 * @author Yuhi Ishikura
 */
public class TeacherRemoteEventListenerDecorator extends TaRemoteEventListenerDecorator {

  /**
   * {@link TeacherRemoteEventListenerDecorator}オブジェクトを構築します。
   * 
   * @param listener リスナ
   */
  public TeacherRemoteEventListenerDecorator(TeacherRemoteEventListenerDecorator listener) {
    super(listener);
  }

}