package org.mklab.taskit.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * 行数を制限し、複数列に分割することができるテーブルです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
abstract class MultiColumnTable extends HorizontalPanel implements ClickHandler {

  List<FlexTable> tables;
  int maximumRowCount = 20;
  int columnHeaderRows = 0;
  List<CellClickListener> listeners;

  /**
   * {@link MultiColumnTable}オブジェクトを構築します。
   */
  MultiColumnTable() {
    this.tables = new ArrayList<FlexTable>();
    this.listeners = new ArrayList<MultiColumnTable.CellClickListener>();
    initTable();
  }

  void addCellClickListener(CellClickListener l) {
    this.listeners.add(l);
  }

  void removeCellClickListener(CellClickListener l) {
    this.listeners.remove(l);
  }

  /**
   * テーブルを初期状態にします。
   */
  private void initTable() {
    this.tables.clear();
    clear();

    addNewTable();
  }

  /**
   * 新しいテーブルを追加します。
   * 
   * @return 新しく生成したテーブル
   */
  private FlexTable addNewTable() {
    final FlexTable table = createTable();
    this.tables.add(table);
    add(table);
    return table;
  }

  /**
   * 新しいテーブルを作成します。
   * 
   * @return 新しいテーブル
   */
  private FlexTable createTable() {
    final FlexTable table = new FlexTable();
    table.addClickHandler(this);
    initTableBase(table);
    return table;
  }

  /**
   * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
   */
  @Override
  public void onClick(ClickEvent event) {
    final FlexTable table = (FlexTable)event.getSource();
    int tableIndex = -1;
    for (int i = 0; i < this.tables.size(); i++) {
      if (table == this.tables.get(i)) {
        tableIndex = i;
        break;
      }
    }
    final Cell cell = table.getCellForEvent(event);
    final int row = tableIndex * this.maximumRowCount + cell.getRowIndex() - this.columnHeaderRows;
    final int column = cell.getCellIndex();
    if (row < 0) return;

    for (CellClickListener l : this.listeners) {
      l.cellClicked(row, column);
    }
  }

  /**
   * テーブルの初期化を行います。
   * <p>
   * 例えば、列ヘッダの生成を行ないます。
   * 
   * @param table 初期化するテーブル。
   */
  abstract void initTableBase(FlexTable table);

  /**
   * 列ヘッダの行数を設定します。
   * 
   * @param columnHeaderRows 列ヘッダの行数
   */
  void setColumnHeaderRows(int columnHeaderRows) {
    this.columnHeaderRows = columnHeaderRows;
  }

  void setText(int row, int column, String text) {
    final FlexTable table = getTableAtRow(row);
    table.setText(rowIndexToLogicalIndex(row), column, text);
  }

  String getText(int row, int column) {
    final FlexTable table = getTableAtRow(row);
    return table.getText(rowIndexToLogicalIndex(row), column);
  }

  void setWidget(int row, int column, Widget widget) {
    final FlexTable table = getTableAtRow(row);
    table.setWidget(rowIndexToLogicalIndex(row), column, widget);
  }

  Widget getWidget(int row, int column) {
    final FlexTable table = getTableAtRow(row);
    return table.getWidget(rowIndexToLogicalIndex(row), column);
  }

  int getRowCount() {
    int rowCount = 0;
    for (int i = 0; i < this.tables.size(); i++) {
      rowCount += this.tables.get(i).getRowCount() - this.columnHeaderRows;
    }
    return rowCount;
  }

  private FlexTable getTableAtRow(int row) {
    final int tableIndex = row / this.maximumRowCount;
    while (tableIndex >= this.tables.size()) {
      addNewTable();
    }
    return this.tables.get(tableIndex);
  }

  private int rowIndexToLogicalIndex(int row) {
    return this.columnHeaderRows + (row % this.maximumRowCount);
  }

  void clearTables() {
    initTable();
  }

  /**
   * セルのクリックを監視するリスナです。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 30, 2011
   */
  static interface CellClickListener {

    /**
     * セルがクリックされたときに呼び出されます。
     * 
     * @param row 行
     * @param column 列
     */
    void cellClicked(int row, int column);
  }

}