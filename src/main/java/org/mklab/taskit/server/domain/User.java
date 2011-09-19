package org.mklab.taskit.server.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.mklab.taskit.shared.model.UserType;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class User extends AbstractEntity<String> {

  private String id;
  private UserType type;
  private String name;

  UserType getType() {
    return this.type;
  }

  void setType(UserType type) {
    this.type = type;
  }

  String getName() {
    return this.name;
  }

  void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(String id) {
    this.id = id;
  }

}
