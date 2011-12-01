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
 * クライアントサイドで用いるモデルクラスを含むパッケージです。<p>
 * 
 * サーバーからEntityProxyとして受け取れるため、モデルクラスは本来必要ありませんが
 * まだ生徒の提出状況の一括取得方法が定まってないため、また無駄な通信を避けるため(講義データ、課題データは毎回取得する必要はない)に
 * クライアント側でも新たにモデルクラスを再定義しています。
 */
package org.mklab.taskit.client.model;