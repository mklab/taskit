package org.mklab.taskit.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/yuhi/workspace/taskit/src/main/resources/org/mklab/taskit/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Access to Database".
   * 
   * @return translated "Access to Database"
   */
  @DefaultMessage("Access to Database")
  @Key("accessToDatabaseButton")
  String accessToDatabaseButton();

  /**
   * Translated "Access to Hibernate".
   * 
   * @return translated "Access to Hibernate"
   */
  @DefaultMessage("Access to Hibernate")
  @Key("accessToHibernateButton")
  String accessToHibernateButton();
}
