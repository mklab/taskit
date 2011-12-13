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