/**
 * TASKitで独自に行う認証を定義したパッケージです。<p>
 * 
 * RequestFactoryに標準の認証ではユーザーの概念がTASKitと適合しなかったため、独自に作成しました。<br>
 * {@link com.google.web.bindery.requestfactory.shared.Request}の実装メソッドに
 * {@link org.mklab.taskit.server.auth.Invoker}アノテーションを付加することにより
 * 実行可能なユーザーを限定することが出来ます。
 * 
 * {@link com.google.web.bindery.requestfactory.shared.Request}で直接参照するメソッドにしか適用されないことに注意してください。
 * 例えば、
 * {@link com.google.web.bindery.requestfactory.shared.Request}で参照したメソッド内で、別の
 * {@link com.google.web.bindery.requestfactory.shared.Request}メソッドを呼び出していた場合には、後者のアクセス制限は適用されません。
 */
package org.mklab.taskit.server.auth;