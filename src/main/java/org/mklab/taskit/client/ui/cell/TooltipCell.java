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

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 * @param <C> セルの値の型
 */
public abstract class TooltipCell<C> extends AbstractCell<C> {

  /**
   * {@link TooltipCell}オブジェクトを構築します。
   */
  public TooltipCell() {
    super("click"); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({"nls", "unused"})
  @Override
  public void render(com.google.gwt.cell.client.Cell.Context context, C value, SafeHtmlBuilder sb) {
    sb.appendHtmlConstant("<a href='#' onclick='return false' style='background-color:#bbb;color:#fff;font-size:0.5em;font-weight:bold;cursor:hand;'>");
    sb.appendEscaped("?");
    sb.appendHtmlConstant("</a>");
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({"nls", "unused"})
  @Override
  public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, C value, NativeEvent event, ValueUpdater<C> valueUpdater) {
    if ("click".equals(event.getType()) == false) return;

    final PopupPanel popup = new PopupPanel();
    popup.setAutoHideEnabled(true);
    popup.add(getTooltipOf(value));
    popup.setPopupPosition(event.getClientX(), event.getClientY());
    popup.show();
  }

  /**
   * セルの値のツールチップを生成します。
   * 
   * @param value 値
   * @return ツールチップを表示するウィジェット
   */
  protected abstract Widget getTooltipOf(C value);
}
