package org.mklab.taskit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
@Entity(name = "test")
public class Test {

	@Id
	private int hoge;

	/**
	 * hogeを取得します。
	 * 
	 * @return hoge
	 */
	public int getHoge() {
		return this.hoge;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Test [hoge=" + hoge + "]";
	}
}
