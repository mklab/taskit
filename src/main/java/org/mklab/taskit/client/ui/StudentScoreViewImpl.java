/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public class StudentScoreViewImpl extends AbstractTaskitView implements StudentScoreView {

  private MultiColumnTable table;

  /**
   * {@link StudentScoreViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentScoreViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setTitle(int, String)
   */
  @Override
  public void setTitle(int index, String title) {
    this.table.setText(index, 0, title);
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setEvaluation(int, int,
   *      int)
   */
  @Override
  public void setEvaluation(int index, int no, int evaluation) {
    final ListBox evaluationList = new ListBox();
    evaluationList.addItem("○"); //$NON-NLS-1$
    evaluationList.addItem("△"); //$NON-NLS-1$
    evaluationList.addItem("×"); //$NON-NLS-1$
    this.table.setWidget(index, no + 1, evaluationList);
    evaluationList.setSelectedIndex(evaluation / 34);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.table = new MultiColumnTable() {

      @SuppressWarnings("hiding")
      @Override
      void initTableBase(com.google.gwt.user.client.ui.FlexTable table) {
        table.setBorderWidth(1);
      }
    };
    this.table.setColumnHeaderRows(1);
    return this.table;
  }

}
