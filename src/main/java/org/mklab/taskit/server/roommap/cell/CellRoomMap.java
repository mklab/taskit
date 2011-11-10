/**
 * 
 */
package org.mklab.taskit.server.roommap.cell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部屋の情報を保持するクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class CellRoomMap {

  private Cell[][] cells;
  private Map<String, Cell> userIdToCell = new HashMap<String, Cell>();

  /**
   * CSVファイルからmapfileを読み込みます。
   * 
   * @param file ファイル
   * @return マップ
   * @throws IOException 読み込めなかった場合
   */
  public static CellRoomMap load(File file) throws IOException {
    Reader reader = new InputStreamReader(new FileInputStream(file));
    try {
      return load(reader);
    } catch (IOException ex) {
      throw ex;
    } finally {
      reader.close();
    }
  }

  /**
   * CSVのマップファイルを読み込みます。
   * 
   * @param reader リーダー
   * @return マップ
   * @throws IOException 読み込めなかった場合
   */
  public static CellRoomMap load(Reader reader) throws IOException {
    List<Cell[]> cellList = new ArrayList<Cell[]>();
    final BufferedReader br = new BufferedReader(reader);
    String line;
    while ((line = br.readLine()) != null) {
      line = line.replaceAll(",,", ", ,"); //$NON-NLS-1$ //$NON-NLS-2$
      final String[] s = line.split(","); //$NON-NLS-1$
      if (cellList.size() > 0) {
        if (cellList.get(0).length != s.length) throw new IOException("invalid column count."); //$NON-NLS-1$
      }
      final Cell[] cellsX = new Cell[s.length];
      for (int i = 0; i < cellsX.length; i++) {
        String cell = s[i].trim();
        if (cell.length() == 0) {
          cellsX[i] = Cell.EMPTY_CELL;
        } else {
          cellsX[i] = new Cell(cell);
        }
      }
      cellList.add(cellsX);
    }
    return new CellRoomMap(cellList.toArray(new Cell[cellList.size()][]));
  }

  private CellRoomMap(Cell[][] cells) {
    this.cells = cells;
    for (Cell[] row : cells) {
      for (Cell cell : row) {
        this.userIdToCell.put(cell.getUserId(), cell);
      }
    }
  }

  /**
   * (x,y)のセルを取得します。
   * 
   * @param x x座標
   * @param y y座標
   * @return セル
   */
  public Cell getCell(int x, int y) {
    return this.cells[y][x];
  }

  /**
   * 与えられたユーザーのセルを取得します。
   * 
   * @param userId ユーザーID
   * @return セル。存在しなければnull
   */
  public Cell getCellFor(String userId) {
    return this.userIdToCell.get(userId);
  }

  /**
   * 横方向のセルの数を取得します。
   * 
   * @return 横方向のセルの数
   */
  public int getCellCountX() {
    return this.cells[0].length;
  }

  /**
   * 横方向のセルの数を取得します。
   * 
   * @return 横方向のセルの数
   */
  public int getCellCountY() {
    return this.cells.length;
  }

}
