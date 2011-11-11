/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;


/**
 * @author Yuhi Ishikura
 */
public class RecordView extends Composite {

  static interface Binder extends UiBinder<Composite, RecordView> {
    // noting to do
  }

  @UiField
  Label rankLabel;
  @UiField
  Label rank;
  @UiField
  Label scoreLabel;
  @UiField
  Label score;
  @UiField
  Label deviationLabel;
  @UiField
  Label deviation;

  /**
   * {@link RecordView}オブジェクトを構築します。
   */
  public RecordView() {
    final Binder binder = GWT.create(Binder.class);
    initWidget(binder.createAndBindUi(this));
  }

}
