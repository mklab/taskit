/**
 * 
 */
package org.mklab.taskit.shared.model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 28, 2011
 */
@Entity(name = "STUDENT")
public class Student {

  /** アカウントのIDです。 */
  @Id
  private String accountId;

  /** 学籍番号です。 */
  private String studentNo;
}
