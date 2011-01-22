package org.mklab.taskit.client;

import java.util.List;

import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.model.Test;
import org.mklab.taskit.service.DBSampleService;
import org.mklab.taskit.service.DBSampleServiceAsync;
import org.mklab.taskit.service.HibernateSampleService;
import org.mklab.taskit.service.HibernateSampleServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Taskit implements EntryPoint {

  final DBSampleServiceAsync dbSampleService = GWT.create(DBSampleService.class);
  final HibernateSampleServiceAsync hibernateSampleService = GWT.create(HibernateSampleService.class);
  private final Messages messages = GWT.create(Messages.class);

  private Label resultLabel;

  /**
   * This is the entry point method.
   */
  @Override
  public void onModuleLoad() {
    final Widget buttonPanel = createButtonPanel();

    this.resultLabel = new Label();

    final RootPanel buttonContainer = RootPanel.get("buttonsContainer"); //$NON-NLS-1$
    final RootPanel resultContainer = RootPanel.get("resultContainer"); //$NON-NLS-1$
    buttonContainer.add(buttonPanel);
    resultContainer.add(this.resultLabel);
    resultContainer.setHeight("3em"); //$NON-NLS-1$
    resultContainer.setWidth("50em"); //$NON-NLS-1$

    RootPanel.get().add(new LoginView());
  }

  private Widget createButtonPanel() {
    final HorizontalPanel buttonPanel = new HorizontalPanel();

    final Button dbAccessButton = new Button(this.messages.accessToDatabaseButton());
    dbAccessButton.addClickHandler(new ClickHandler() {

      @Override
      @SuppressWarnings("unused")
      public void onClick(ClickEvent event) {
        Taskit.this.dbSampleService.accessToDatabase(new AsyncCallback<String>() {

          @Override
          public void onFailure(Throwable caught) {
            failed(caught);
          }

          @Override
          public void onSuccess(String result) {
            setResultText(result);
          }
        });
      }
    });
    final Button hibernateAccessButton = new Button(this.messages.accessToHibernateButton());
    hibernateAccessButton.addClickHandler(new ClickHandler() {

      @Override
      @SuppressWarnings("unused")
      public void onClick(ClickEvent event) {
        Taskit.this.hibernateSampleService.accessThroughHibernate(new AsyncCallback<List<Test>>() {

          @Override
          public void onFailure(Throwable caught) {
            failed(caught);
          }

          @Override
          public void onSuccess(List<Test> result) {
            setResultText(result.toString());
          }
        });
      }
    });

    buttonPanel.add(dbAccessButton);
    buttonPanel.add(hibernateAccessButton);
    return buttonPanel;
  }

  void setResultText(String text) {
    this.resultLabel.setText(text);
  }

  void failed(Throwable e) {
    setResultText("Fail : " + e.toString()); //$NON-NLS-1$
  }

}
