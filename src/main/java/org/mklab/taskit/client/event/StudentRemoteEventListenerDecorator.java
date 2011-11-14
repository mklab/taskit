/**
 * 
 */
package org.mklab.taskit.client.event;

import org.mklab.taskit.shared.event.MyHelpCallEvent;
import org.mklab.taskit.shared.event.MyRecordChangeEvent;

import de.novanic.eventservice.client.event.Event;


/**
 * 学生用のリモートイベントリスナです。
 * 
 * @author Yuhi Ishikura
 */
public class StudentRemoteEventListenerDecorator implements RemoteEventListenerDecorator<StudentRemoteEventListenerDecorator> {

  private StudentRemoteEventListenerDecorator listener;

  /**
   * {@inheritDoc}
   */
  @Override
  public final void apply(Event event) {
    if (event instanceof MyHelpCallEvent) {
      myHelpCallChanged((MyHelpCallEvent)event);
    } else if (event instanceof MyRecordChangeEvent) {
      myRecordChanged((MyRecordChangeEvent)event);
    }
  }

  /**
   * 自分の成績に変更があったときに呼び出されます。
   * 
   * @param evt イベント
   */
  public void myRecordChanged(MyRecordChangeEvent evt) {
    if (this.listener != null) this.listener.myRecordChanged(evt);
  }

  /**
   * ヘルプコールに変更があったときに呼び出されます。
   * 
   * @param evt イベント
   */
  public void myHelpCallChanged(MyHelpCallEvent evt) {
    if (this.listener != null) this.listener.myHelpCallChanged(evt);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListener(StudentRemoteEventListenerDecorator listener) {
    this.listener = listener;
  }

}