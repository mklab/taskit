/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import java.util.HashMap;
import java.util.Map;

import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;

import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class LectureReportChooser extends Composite {

  Tree tree;
  Map<TreeItem, Object> treeItemToModel;
  Map<Object, TreeItem> modelToTreeItem;
  Presenter presenter;

  /**
   * {@link ReportEditorImpl.ReportLectureTree}オブジェクトを構築します。
   */
  public LectureReportChooser() {
    this.tree = new Tree();
    this.treeItemToModel = new HashMap<TreeItem, Object>();
    this.modelToTreeItem = new HashMap<Object, TreeItem>();

    final ScrollPanel pn = new ScrollPanel(this.tree);
    initWidget(pn);

    this.tree.addOpenHandler(new OpenHandler<TreeItem>() {

      @Override
      public void onOpen(OpenEvent<TreeItem> event) {
        final Object selectedModel = getModelOf(event.getTarget());
        if (selectedModel instanceof Report) return;
        LectureReportChooser.this.presenter.lectureNodeOpenning((Lecture)selectedModel);
      }
    });
  }

  /**
   * モデル(講義or課題)を取得します。
   * 
   * @param item ツリーアイテム
   * @return モデル
   */
  public Object getModelOf(TreeItem item) {
    return this.treeItemToModel.get(item);
  }

  void setLectures(Lecture[] lectures) {
    this.tree.clear();
    this.treeItemToModel.clear();
    for (Lecture l : lectures) {
      final TreeItem lectureNode = createLectureNode(l);

      lectureNode.addItem("Now loading..."); //$NON-NLS-1$
      this.tree.addItem(lectureNode);
    }
  }

  void setReports(Lecture l, Report[] reports) {
    final TreeItem targetLectureNode = this.modelToTreeItem.get(l);
    for (int i = 0; i < targetLectureNode.getChildCount(); i++) {
      final TreeItem child = targetLectureNode.getChild(i);
      final Object v = this.treeItemToModel.remove(child);
      this.modelToTreeItem.remove(v);
    }
    targetLectureNode.removeItems();
    for (Report report : reports) {
      targetLectureNode.addItem(createReportNode(report));
    }
  }

  private TreeItem createLectureNode(Lecture lecture) {
    TreeItem item = new TreeItem("<b>" + lecture.getTitle() + "</b>"); //$NON-NLS-1$ //$NON-NLS-2$
    this.treeItemToModel.put(item, lecture);
    return item;
  }

  private TreeItem createReportNode(Report report) {
    final TreeItem item = new TreeItem(report.getTitle());
    this.treeItemToModel.put(item, report);
    this.modelToTreeItem.put(report, item);
    return item;
  }

  /**
   * presenterを設定します。
   * 
   * @param presenter presenter
   */
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Feb 1, 2011
   */
  public static interface Presenter {

    /**
     * 講義ノードが選択されたときに呼び出されます。
     * 
     * @param l 講義ノード
     */
    void lectureNodeOpenning(Lecture l);
  }

}
