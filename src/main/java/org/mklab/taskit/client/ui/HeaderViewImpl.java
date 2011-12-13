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
package org.mklab.taskit.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class HeaderViewImpl extends Composite implements HeaderView {

  interface Binder extends UiBinder<Widget, HeaderViewImpl> {
    // empty
  }

  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  HorizontalPanel buttonPanel;
  @UiField
  InlineLabel userIdLabel;
  @UiField
  InlineLabel userTypeLabel;

  /**
   * {@link HeaderViewImpl}オブジェクトを構築します。
   */
  public HeaderViewImpl() {
    initWidget(binder.createAndBindUi(this));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ToolBarButton addButton(String name) {
    final PushButton button = new PushButton(name);
    addImpl(button);
    return new ToolBarButton() {

      HandlerRegistration lastClickHandler;

      @Override
      public void setName(@SuppressWarnings("hiding") String name) {
        button.setText(name);
      }

      @Override
      public void setIcon(String icon) {
        final Image image = new Image(icon);
        image.setSize("24pt", "24pt"); //$NON-NLS-1$ //$NON-NLS-2$
        button.getUpFace().setImage(image);
      }

      @Override
      public void setClickHandler(ClickHandler h) {
        if (this.lastClickHandler != null) {
          this.lastClickHandler.removeHandler();
        }
        this.lastClickHandler = button.addClickHandler(h);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSeparator() {
    addImpl(new Label("|")); //$NON-NLS-1$
  }

  private void addImpl(Widget w) {
    this.buttonPanel.add(w);
    this.buttonPanel.setCellVerticalAlignment(w, HasVerticalAlignment.ALIGN_MIDDLE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeight() {
    return 32;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserId(String id) {
    this.userIdLabel.setText(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserType(String type) {
    this.userTypeLabel.setText(type);
  }

}
