/**
 * データの入出力を行うパッケージです。
 * <p>
 * 独自のバックアップ機能を提供します。<br>
 * 
 * 提供する予定でしたが、インポートは今のところ行うのが困難です。
 * JPAで、エンティティのIDを自動生成していますが、それによりエクスポートされたIDを復元することができません。
 * エンティティのクラスのIdのGeneratedValueアノテーションを一時的に削除すればおそらく実行可能です。
 * 
 * 素直にphpMyAdminなどのデータベース固有のバックアップツールを利用しましょう。
 */
package org.mklab.taskit.server.io;