/**
 * 
 */
package org.mklab.taskit.client.ui;

import java.util.List;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;


/**
 * 提出物の状態を編集するセルです。
 * <p>
 * コンボボックスにより編集を行います。列にそもそも課題がない場合には、編集ができないようにしています。
 * 
 * @author ishikura
 */
class SelectCell extends AbstractInputCell<String, String> {

  private List<String> options;
  private boolean editable;

  /**
   * {@link SelectCell}オブジェクトを構築します。
   * 
   * @param options 選択可能なオプションのリスト
   */
  public SelectCell(List<String> options) {
    super("change"); //$NON-NLS-1$
    this.options = options;
  }

  /**
   * editableを設定します。
   * 
   * @param editable editable
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
    if ("change".equals(event.getType()) == false) return; //$NON-NLS-1$

    final SelectElement select = parent.getFirstChild().cast();
    final String selectedValue = select.getValue();

    finishEditing(parent, value, context.getKey(), valueUpdater);
    if (valueUpdater != null) {
      valueUpdater.update(selectedValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("nls")
  @Override
  public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
    if (value == null) return;

    if (this.editable) {
      sb.appendHtmlConstant("<select>");
    } else {
      sb.appendHtmlConstant("<select disabled>");
    }

    for (String option : this.options) {
      final boolean selected = option.equals(value);
      final String escapedOption = SafeHtmlUtils.htmlEscape(option);
      sb.appendHtmlConstant("<option value='" + escapedOption + "'" + (selected ? " selected" : "") + ">" + escapedOption + "</option>");
    }
    sb.appendHtmlConstant("</select>");
  }

}