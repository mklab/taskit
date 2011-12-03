/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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