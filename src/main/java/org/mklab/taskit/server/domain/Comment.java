package org.mklab.taskit.server.domain;

import javax.persistence.Embeddable;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Embeddable
public class Comment {

  private String comment;
  private String secretComment;

  String getComment() {
    return this.comment;
  }

  void setComment(String comment) {
    this.comment = comment;
  }

  String getSecretComment() {
    return this.secretComment;
  }

  void setSecretComment(String secretComment) {
    this.secretComment = secretComment;
  }

}
