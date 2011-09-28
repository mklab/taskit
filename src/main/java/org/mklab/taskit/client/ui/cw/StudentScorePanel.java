/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class StudentScorePanel extends Composite {

  interface StudentScoreUiBinder extends UiBinder<Widget, StudentScorePanel> {
    // empty
  }

  private static StudentScoreUiBinder uiBinder = GWT.create(StudentScoreUiBinder.class);

  /**
   * {@link StudentScorePanel}オブジェクトを構築します。
   */
  public StudentScorePanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
