/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 28, 2011
 */
public class ReportEditor2Impl extends Composite implements ReportEditor2 {

  private TreeGrid tree;
  private Tree data;

  /**
   * {@link ReportEditor2Impl}オブジェクトを構築します。
   */
  public ReportEditor2Impl() {
    this.tree = new TreeGrid();
    this.tree.setCanDrag(Boolean.TRUE);

    this.tree.setCanEdit(Boolean.TRUE);
    this.tree.setCanReorderRecords(Boolean.TRUE);
    this.tree.setCanDrop(Boolean.TRUE);
    this.tree.setCanDrag(Boolean.TRUE);

    final TreeGridField titleField = new TreeGridField("title"); //$NON-NLS-1$
    this.tree.setFields(titleField);

    this.data = new Tree();
    this.tree.setData(this.data);
    this.data.add(createNode("test", "aiueo"), this.data.getRoot());

    initWidget(this.tree);

    this.tree.addCellDoubleClickHandler(new CellDoubleClickHandler() {

      @Override
      public void onCellDoubleClick(CellDoubleClickEvent event) {
        data.add(createNode("test", "aiueo"), data.getRoot());
      }
    });
  }

  private TreeNode createNode(String name, String title) {
    TreeNode node = new TreeNode(name);
    node.setTitle(title);
    return node;
  }

  /**
   * T
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return this;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor2#setLecureTitle(int,
   *      java.lang.String)
   */
  @Override
  public void setLecureTitle(int index, String title) {

  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor2#setReportTitle(int,
   *      int, java.lang.String)
   */
  @Override
  public void setReportTitle(int lectureIndex, int reportIndex, String title) {
    // TODO Auto-generated method stub

  }

}
