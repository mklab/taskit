/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.ui.AdminView;
import org.mklab.taskit.client.ui.AdminViewImpl;
import org.mklab.taskit.client.ui.NewAccountView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.model.UserType;
import org.mklab.taskit.shared.service.AccountService;
import org.mklab.taskit.shared.service.AccountServiceAsync;
import org.mklab.taskit.shared.validation.AccountValidator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class AdminActivity extends TaskitActivity {

  /**
   * {@link AdminActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(final ClientFactory clientFactory) {
    final AdminView view = new AdminViewImpl(clientFactory);
    final NewAccountView newAccountView = view.getNewAccountView();

    final Messages messages = clientFactory.getMessages();

    final UserType[] choosableAccountTypes = new UserType[] {UserType.STUDENT, UserType.TA};
    newAccountView.setChoosableAccountTypes(localizeUserTypes(messages, choosableAccountTypes));

    newAccountView.getSubmitTrigger().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        final String id = newAccountView.getUserId();
        final String password = newAccountView.getPassword();
        final String passwordForConfirmation = newAccountView.getPasswordForComfimation();
        final int accountTypeIndex = newAccountView.getAccountType();

        if (id.length() == 0) {
          showErrorMessage(messages.isBlankMessage(messages.idLabel()));
          return;
        }
        if (password.length() == 0) {
          showErrorMessage(messages.isBlankMessage(messages.passwordLabel()));
          return;
        }
        if (accountTypeIndex == -1) {
          showErrorMessage(messages.isBlankMessage(messages.accountTypeLabel()));
          return;
        }
        if (password.equals(passwordForConfirmation) == false) {
          showErrorMessage(messages.passwordConfirmationFailMessage());
          return;
        }

        switch (AccountValidator.validate(id, password)) {
          case TOO_SHORT_ID:
            showErrorMessage(messages.idTooShortMessage());
            return;
          case TOO_SHORT_PASSWORD:
            showErrorMessage(messages.passwordTooShortMessage());
            return;
          case ID_EQUALS_TO_PASSWORD:
            showErrorMessage(messages.idEqualsPasswordMessage());
            return;
          case VALID:
            break;
        }

        final AccountServiceAsync service = GWT.create(AccountService.class);
        service.createNewAccount(id, password, choosableAccountTypes[accountTypeIndex].name(), new AsyncCallback<Void>() {

          @SuppressWarnings("unused")
          @Override
          public void onSuccess(Void result) {
            showInformationMessage(messages.accountRegistrationSuccessMessage(id));
          }

          @Override
          public void onFailure(Throwable caught) {
            showErrorMessage(caught.getMessage());
          }
        });
      }
    });
    return view;
  }

  private String[] localizeUserTypes(final Messages messages, final UserType[] userTypes) {
    final String[] localizedUserTypes = new String[userTypes.length];
    for (int i = 0; i < localizedUserTypes.length; i++) {
      String localizedType = null;
      switch (userTypes[i]) {
        case STUDENT:
          localizedType = messages.studentLabel();
          break;
        case TA:
          localizedType = messages.taLabel();
          break;
        case TEACHER:
          localizedType = messages.teacherLabel();
          break;
        default:
          throw new IllegalStateException();
      }
      localizedUserTypes[i] = localizedType;
    }
    return localizedUserTypes;
  }
}
