package org.mklab.taskit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
@Entity(name = "test")
public class Test extends LightEntity {

  /** */
  private static final long serialVersionUID = -6891624458290077770L;
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
   * hogeを設定します。
   * 
   * @param hoge hoge
   */
  public void setHoge(int hoge) {
    this.hoge = hoge;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @SuppressWarnings("nls")
  @Override
  public String toString() {
    return "Test [hoge=" + this.hoge + "]";
  }

}
