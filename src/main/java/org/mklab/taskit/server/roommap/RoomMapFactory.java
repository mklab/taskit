/**
 * 
 */
package org.mklab.taskit.server.roommap;

import java.awt.image.BufferedImage;


/**
 * どのクライアントがどこにいるかのマップを生成するインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface RoomMapFactory {

  /**
   * 与えられたユーザーの場所を示すマップ画像を取得します。
   * 
   * @param userId ユーザーID
   * @return マップ画像
   */
  BufferedImage getRoomMapFor(String userId);

}
