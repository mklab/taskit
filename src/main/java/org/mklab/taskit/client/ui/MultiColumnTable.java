package org.mklab.taskit.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * 行数を制限し、複数列に分割することができるテーブルです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
abstract class MultiColumnTable extends HorizontalPanel {

  List<FlexTable> tables;
  int maximumRowCount = 20;
  int columnHeaderRows = 0;

  /**
   * {@link MultiColumnTable}オブジェクトを構築します。
   */
  MultiColumnTable() {
    this.tables = new ArrayList<FlexTable>();
    initTable();
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
    initTableBase(table);
    return table;
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

  void setWidget(int row, int column, Widget widget) {
    final FlexTable table = getTableAtRow(row);
    table.setWidget(rowIndexToLogicalIndex(row), column, widget);
  }

  Widget getWidget(int row, int column) {
    final FlexTable table = getTableAtRow(row);
    return table.getWidget(rowIndexToLogicalIndex(row), column);
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

}