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
 * MVPパターンにおけるビューを含むパッケージです。<p>
 * 
 * <a href="http://code.google.com/intl/en/webtoolkit/doc/latest/DevGuideMvpActivitiesAndPlaces.html" target="_blank">GWT Activities and Places</a>
 * ログイン後のすべてのビューは、{@link org.mklab.taskit.client.ui.TaskitView}を継承すれば作成できますが、
 * 見た目の統一化のため基本的なビューを実装した{@link org.mklab.taskit.client.ui.AbstractTaskitView}を通常は継承してください。
 * 
 * 本来MVPパターンではViewはModelを知るべきではありませんが、そこまで徹底するとビューのインターフェースがかえって複雑になってしまうため、shared内のエンティティなどは直接利用しています。
 */
package org.mklab.taskit.client.ui;