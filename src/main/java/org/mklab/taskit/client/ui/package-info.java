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