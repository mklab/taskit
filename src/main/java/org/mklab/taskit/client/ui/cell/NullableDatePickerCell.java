/**
 * 
 */
package org.mklab.taskit.client.ui.cell;

import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;


/**
 * nullを許容する{@link DatePickerCell}です。
 * 
 * @author ishikura
 */
public final class NullableDatePickerCell extends DatePickerCell {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, Date value, NativeEvent event, ValueUpdater<Date> valueUpdater) {
    if (value == null) {
      super.onBrowserEvent(context, parent, new Date(), event, valueUpdater);
    } else {
      super.onBrowserEvent(context, parent, value, event, valueUpdater);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void render(com.google.gwt.cell.client.Cell.Context context, Date value, SafeHtmlBuilder sb) {
    if (value == null) {
      // セル中のどこをクリックしても編集を開始しますが、分かりやすいようにボタンを表示します。
      sb.appendHtmlConstant("<input type='button' value='Edit'/>"); //$NON-NLS-1$
      return;
    }

    super.render(context, value, sb);
  }
}