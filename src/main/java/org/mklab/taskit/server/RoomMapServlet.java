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
package org.mklab.taskit.server;

import org.mklab.taskit.server.roommap.CellRoomMapFactory;
import org.mklab.taskit.server.roommap.RoomMapFactory;
import org.mklab.taskit.server.roommap.cell.CellRoomMap;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 部屋のマップ画像を提供するサーブレットです。
 * <p>
 * サーブレットのルートディレクトリの「map.csv」ファイルを読み込み画像を生成します。
 * 
 * @author Yuhi Ishikura
 */
public class RoomMapServlet extends HttpServlet {

  /** for serialization. */
  private static final long serialVersionUID = 6553773636274894534L;
  private RoomMapFactory mapFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    initMapFactory(config.getServletContext());
  }

  private void initMapFactory(ServletContext context) {
    final String mapFilePath = context.getRealPath("/map.csv"); //$NON-NLS-1$
    final File mapFile = new File(mapFilePath);
    if (mapFile.exists() == false) {
      log("Map file not exists. : " + mapFilePath); //$NON-NLS-1$
      this.mapFactory = null;
      return;
    }

    CellRoomMap roomMap;
    try {
      roomMap = CellRoomMap.load(mapFile);
      this.mapFactory = new CellRoomMapFactory(roomMap);
    } catch (IOException e) {
      log("Failed to load map file. : " + mapFile, e); //$NON-NLS-1$
      this.mapFactory = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("nls")
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (this.mapFactory == null) {
      response.getWriter().append("Room map not initialized.").close();
      return;
    }

    final String userId = request.getParameter("id");
    response.setHeader("Cache-Control", "public");
    response.setContentType("image/jpeg");
    response.setHeader("Content-Disposition", "inline; filename=\"map" + userId + "\"");

    final RenderedImage image = this.mapFactory.getRoomMapFor(userId);
    final OutputStream os = response.getOutputStream();
    ImageIO.write(image, "png", os);
    os.close();
  }

}
