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

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;


/**
 * @author ishikura
 */
public class TextAreaCell extends AbstractInputCell<String, String> {

  /**
   * {@link TextAreaCell}オブジェクトを構築します。
   */
  public TextAreaCell() {
    super("change"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({"nls", "unused"})
  @Override
  public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
    sb.appendHtmlConstant("<textarea rows='2'>");
    if (value != null) sb.appendEscaped(value);
    sb.appendHtmlConstant("</textarea>");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
    super.onBrowserEvent(context, parent, value, event, valueUpdater);
    if ("change".equals(event.getType())) { //$NON-NLS-1$
      finishEditing(parent, value, context.getKey(), valueUpdater);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onEnterKeyDown(com.google.gwt.cell.client.Cell.Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
    if (event.getCtrlKey()) {
      super.onEnterKeyDown(context, parent, value, event, valueUpdater);
    }
    return;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void finishEditing(Element parent, String value, Object key, ValueUpdater<String> valueUpdater) {
    final InputElement input = getInputElement(parent).cast();
    valueUpdater.update(input.getValue());
    super.finishEditing(parent, value, key, valueUpdater);
  }
}
