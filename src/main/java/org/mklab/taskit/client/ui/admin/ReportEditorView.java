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
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.cell.NullableDatePickerCell;
import org.mklab.taskit.client.ui.cell.SelectCell;
import org.mklab.taskit.client.ui.cell.TextAreaCell;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.cellview.client.Column;


/**
 * @author yuhi
 */
public class ReportEditorView extends AbstractEntityEditorView<ReportProxy> {

  /**
   * {@link ReportEditorView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public ReportEditorView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initTable() {
    final Column<ReportProxy, String> title = createTitleColumn();
    final Column<ReportProxy, String> description = createDescriptionColumn();
    final Column<ReportProxy, String> point = createPointColumn();
    final Column<ReportProxy, Date> period = createPeriodColumn();
    final Column<ReportProxy, String> delete = createDeleteColumn();

    this.table.addColumn(title, getClientFactory().getMessages().titleLabel());
    this.table.addColumn(description, getClientFactory().getMessages().descriptionLabel());
    this.table.addColumn(point, getClientFactory().getMessages().pointLabel());
    this.table.addColumn(period, getClientFactory().getMessages().periodLabel());
    this.table.addColumn(delete);
  }

  /**
   * 講義データを設定します。
   * 
   * @param lectures 講義データ
   */
  public void setChoosableLectures(List<LectureProxy> lectures) {
    this.table.insertColumn(0, createLectureColumn(lectures), getClientFactory().getMessages().lectureLabel());
  }

  private Column<ReportProxy, LectureProxy> createLectureColumn(List<LectureProxy> lectures) {
    final List<LectureProxy> options = new ArrayList<LectureProxy>(lectures);
    options.add(null);
    final SelectCell.Renderer<LectureProxy> lectureRenderer = new SelectCell.Renderer<LectureProxy>() {

      @SuppressWarnings({"deprecation", "synthetic-access"})
      @Override
      public String render(int index, LectureProxy value) {
        if (value == null) return ""; //$NON-NLS-1$
        final String lectureIndexLabel = getClientFactory().getMessages().lectureIndexLabel(String.valueOf(index + 1));
        return lectureIndexLabel + " " + value.getDate().toLocaleString(); //$NON-NLS-1$
      }

    };
    final SelectCell.Comparator<LectureProxy> lectureComparator = new SelectCell.Comparator<LectureProxy>() {

      @Override
      public boolean equals(LectureProxy e1, LectureProxy e2) {
        if (e1 == null) {
          if (e2 == null) return true;
          return false;
        }

        if (e2 == null) return false;

        return e1.getId().equals(e2.getId());
      }

    };
    final Column<ReportProxy, LectureProxy> lectureColumn = new Column<ReportProxy, LectureProxy>(new SelectCell<LectureProxy>(options, lectureRenderer, lectureComparator)) {

      @Override
      public LectureProxy getValue(ReportProxy object) {
        return object.getLecture();
      }
    };
    lectureColumn.setFieldUpdater(new FieldUpdater<ReportProxy, LectureProxy>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, ReportProxy object, LectureProxy value) {
        final ReportProxy report = getPresenter().edit(object);
        report.setLecture(value);
        getPresenter().save(report);
      }
    });
    return lectureColumn;
  }

  private Column<ReportProxy, String> createTitleColumn() {
    final Column<ReportProxy, String> titleColumn = new Column<ReportProxy, String>(new TextInputCell()) {

      @Override
      public String getValue(ReportProxy object) {
        return object.getTitle();
      }
    };
    titleColumn.setFieldUpdater(new FieldUpdater<ReportProxy, String>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, ReportProxy object, String value) {
        final ReportProxy report = getPresenter().edit(object);
        report.setTitle(value);
        getPresenter().save(report);
      }
    });
    return titleColumn;
  }

  private Column<ReportProxy, String> createDescriptionColumn() {
    final Column<ReportProxy, String> description = new Column<ReportProxy, String>(new TextAreaCell()) {

      @Override
      public String getValue(ReportProxy object) {
        return object.getDescription();
      }
    };
    description.setFieldUpdater(new FieldUpdater<ReportProxy, String>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, ReportProxy object, String value) {
        final ReportProxy report = getPresenter().edit(object);
        report.setDescription(value);
        getPresenter().save(report);
      }
    });
    return description;
  }

  private Column<ReportProxy, String> createPointColumn() {
    final TextInputCell integerCell = new TextInputCell() {

      @Override
      protected void finishEditing(Element parent, String value, Object key, ValueUpdater<String> valueUpdater) {
        try {
          Integer.parseInt(value);
        } catch (Throwable e) {
          return;
        }
        super.finishEditing(parent, value, key, valueUpdater);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
        if ("keyup".equals(event.getType())) { //$NON-NLS-1$
          final InputElement input = getInputElement(parent);
          if (isNumber(input.getValue()) == false) {
            if (isNumber(value)) {
              input.setValue(value);
            } else {
              input.setValue("1"); //$NON-NLS-1$
            }
          }
        }

        super.onBrowserEvent(context, parent, value, event, valueUpdater);
      }

      private boolean isNumber(String text) {
        try {
          Integer.parseInt(text);
          return true;
        } catch (Throwable e) {
          return false;
        }
      }

    };
    final Column<ReportProxy, String> pointColumn = new Column<ReportProxy, String>(integerCell) {

      @Override
      public String getValue(ReportProxy object) {
        return String.valueOf(object.getPoint());
      }
    };
    pointColumn.setFieldUpdater(new FieldUpdater<ReportProxy, String>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, ReportProxy object, String value) {
        final ReportProxy report = getPresenter().edit(object);
        report.setPoint(Integer.parseInt(value));
        getPresenter().save(report);
      }

    });
    return pointColumn;
  }

  private Column<ReportProxy, Date> createPeriodColumn() {
    final Column<ReportProxy, Date> periodColumn = new Column<ReportProxy, Date>(new NullableDatePickerCell()) {

      @Override
      public Date getValue(ReportProxy object) {
        return object.getPeriod();
      }

    };
    periodColumn.setFieldUpdater(new FieldUpdater<ReportProxy, Date>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, ReportProxy object, Date value) {
        final ReportProxy report = getPresenter().edit(object);
        report.setPeriod(value);
        getPresenter().save(report);
      }
    });
    return periodColumn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createDefaultEntity(ReportProxy entity) {
    entity.setPoint(1);
    entity.setTitle(getClientFactory().getMessages().defaultReportTitle());
  }
}
