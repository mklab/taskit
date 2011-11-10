/**
 * 
 */
package org.mklab.taskit.server.roommap;

import org.mklab.taskit.server.roommap.cell.Cell;
import org.mklab.taskit.server.roommap.cell.CellRoomMap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 * @author Yuhi Ishikura
 */
public class CellRoomMapFactory implements RoomMapFactory {

  private static final int BORDER_WIDTH = 1;
  private static final int CELL_WIDTH = 10;

  CellRoomMap roomMap;

  /**
   * {@link CellRoomMapFactory}オブジェクトを構築します。
   * 
   * @param roomMap マップ
   */
  public CellRoomMapFactory(CellRoomMap roomMap) {
    this.roomMap = roomMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BufferedImage getRoomMapFor(String userId) {
    final int width = this.roomMap.getCellCountX() * (CELL_WIDTH + BORDER_WIDTH) + BORDER_WIDTH;
    final int height = this.roomMap.getCellCountY() * (CELL_WIDTH + BORDER_WIDTH) + BORDER_WIDTH;

    final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    final Graphics g = image.getGraphics();

    for (int x = 0; x < this.roomMap.getCellCountX(); x++) {
      for (int y = 0; y < this.roomMap.getCellCountY(); y++) {
        final Cell cell = this.roomMap.getCell(x, y);
        int xx = x * (CELL_WIDTH + BORDER_WIDTH);
        int yy = y * (CELL_WIDTH + BORDER_WIDTH);
        if (cell.isExists() == false) continue;

        g.setColor(Color.BLACK);
        for (int i = 0; i < BORDER_WIDTH; i++) {
          g.drawRect(xx + i, yy + i, CELL_WIDTH + 2 * BORDER_WIDTH - i * 2 - 1, CELL_WIDTH + 2 * BORDER_WIDTH - i * 2 - 1);
        }

        if (cell.getUserId().equals(userId)) {
          g.setColor(Color.RED);
        } else {
          g.setColor(Color.GRAY);
        }
        g.fillRect(xx + BORDER_WIDTH, yy + BORDER_WIDTH, CELL_WIDTH, CELL_WIDTH);
      }
    }

    return image;
  }

}
