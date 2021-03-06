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

import java.util.List;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;


/**
 * 提出物の状態を編集するセルです。
 * <p>
 * コンボボックスにより編集を行います。列にそもそも課題がない場合には、編集ができないようにしています。
 * 
 * @author ishikura
 * @param <E> オプションの型
 */
public class SelectCell<E> extends AbstractInputCell<E, E> {

  private List<E> options;
  private boolean editable = true;
  private Renderer<E> renderer;
  private Comparator<E> comparator;

  /**
   * {@link SelectCell}オブジェクトを構築します。
   * 
   * @param options 選択可能なオプションのリスト
   */
  public SelectCell(List<E> options) {
    this(options, new Renderer<E>() {

      @Override
      public String render(@SuppressWarnings("unused") int index, E value) {
        return String.valueOf(value);
      }
    });
  }

  /**
   * {@link SelectCell}オブジェクトを構築します。
   * 
   * @param options 選択可能なオプションのリスト
   * @param renderer 値を、描画する文字列に変換するオブジェクト
   */
  public SelectCell(List<E> options, Renderer<E> renderer) {
    this(options, renderer, new Comparator<E>() {

      @Override
      public boolean equals(E e1, E e2) {
        if (e1 == null) {
          if (e2 == null) {
            return true;
          }
          return false;
        }
        return e1.equals(e2);
      }
    });
  }

  /**
   * {@link SelectCell}オブジェクトを構築します。
   * 
   * @param options 選択可能なオプションのリスト
   * @param renderer 値を、描画する文字列に変換するオブジェクト
   * @param comparator 値とオプションの比較を行うオブジェクト
   */
  public SelectCell(List<E> options, Renderer<E> renderer, Comparator<E> comparator) {
    super("change"); //$NON-NLS-1$
    if (options == null) throw new NullPointerException();
    if (renderer == null) throw new NullPointerException();
    if (comparator == null) throw new NullPointerException();
    this.options = options;
    this.renderer = renderer;
    this.comparator = comparator;
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
  public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, E value, NativeEvent event, ValueUpdater<E> valueUpdater) {
    if (this.editable == false) return;

    if ("change".equals(event.getType()) == false) return; //$NON-NLS-1$

    final SelectElement select = getSelectElement(parent);
    final int selectedIndex = select.getSelectedIndex();
    if (selectedIndex < 0) return;

    final E selectedValue = this.options.get(selectedIndex);
    finishEditing(parent, value, context.getKey(), valueUpdater);
    if (valueUpdater != null) {
      valueUpdater.update(selectedValue);
    }
  }

  private static SelectElement getSelectElement(Element parent) {
    Node child = parent;
    do {
      if (child.getNodeName().toLowerCase().equals("select")) return child.cast(); //$NON-NLS-1$
    } while ((child = child.getFirstChild()) != null);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("nls")
  @Override
  public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, E value, SafeHtmlBuilder sb) {
    if (this.editable) {
      sb.appendHtmlConstant("<select>");
    } else {
      int index = 0;
      for (E option : this.options) {
        final boolean selected = this.comparator.equals(option, value);
        if (selected) {
          final String escapedOption = SafeHtmlUtils.htmlEscape(this.renderer.render(index++, option));
          sb.appendEscaped(escapedOption);
        }
      }
      return;
    }

    int index = 0;
    for (E option : this.options) {
      final boolean selected = this.comparator.equals(option, value);
      final String escapedOption = SafeHtmlUtils.htmlEscape(this.renderer.render(index++, option));
      sb.appendHtmlConstant("<option value='" + escapedOption + "'" + (selected ? " selected" : "") + ">" + escapedOption + "</option>");
    }
    sb.appendHtmlConstant("</select>");
  }

  /**
   * 値をセルに描画する文字列に変換するクラスです。
   * 
   * @author ishikura
   * @param <E> 値の型
   */
  public static interface Renderer<E> {

    /**
     * 値を文字列にします。
     * 
     * @param index リストのインデックス
     * @param value 値
     * @return 文字列
     */
    String render(int index, E value);

  }

  /**
   * オブジェクトの比較を行うインターフェースです。
   * 
   * @author ishikura
   * @param <E> オブジェクトの型
   */
  public static interface Comparator<E> {

    /**
     * 二つのオブジェクトが同じであるか調べます。
     * 
     * @param e1 オブジェクト
     * @param e2 オブジェクト
     * @return 二つのオブジェクトが同じかどうか
     */
    boolean equals(E e1, E e2);
  }

}