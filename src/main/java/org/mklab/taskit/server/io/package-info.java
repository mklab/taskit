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