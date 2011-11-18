/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordModel.LectureScore;
import org.mklab.taskit.shared.RecordProxy;
import org.mklab.taskit.shared.UserProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 */
public class StudentListViewImpl extends AbstractTaskitView implements StudentListView {

  @UiField(provided = true)
  ValueListBox<UserProxy> userList;
  @UiField(provided = true)
  ValueListBox<SortType> sortTypeList;
  @UiField(provided = true)
  StudentPanel panel;

  @UiField(provided = true)
  Image uncallButton;
  @UiField(provided = true)
  Image reloadButton;
  @UiField(provided = true)
  Image scoreButton;
  @UiField(provided = true)
  Image userInfoButton;
  @UiField(provided = true)
  Image mapButton;
  @UiField
  Label viewersLabel;
  @UiField
  Label viewers;

  @UiField(provided = true)
  Image userIdButton;
  TextBox userIdText;

  private Presenter presenter;
  private List<UserProxy> allUsers;
  private List<UserProxy> listItemUsers;
  private Map<String, RecordProxy> userIdToRecord;
  private static final Binder binder = GWT.create(Binder.class);
  private static SortType lastSortType = SortType.ID_ASCENDING;

  interface Binder extends UiBinder<Widget, StudentListViewImpl> {
    // empty
  }

  static enum SortType {
    ID_ASCENDING, ID_DESCENDING, SCORE_ASCENDING, SCORE_DESCENDING
  }

  /**
   * {@link StudentListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    this.panel.setPresenter(presenter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedListData(UserProxy user) {
    this.userList.setValue(user, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(List<UserProxy> listData) {
    this.allUsers = listData;
    resetListData();
  }

  private void resetListData() {
    if (this.allUsers == null || this.allUsers.size() == 0) {
      this.userList.setAcceptableValues(Collections.<UserProxy> emptyList());
      return;
    }

    final List<UserProxy> sortedListData = filterStudentsByIdOrName();

    final SortType sortType = this.sortTypeList.getValue();
    if (sortType != null && sortType != SortType.ID_ASCENDING) {
      switch (sortType) {
        case ID_DESCENDING:
          Collections.reverse(sortedListData);
          break;
        case SCORE_ASCENDING:
          if (sortByScore(sortedListData, true) == false) return;
          break;
        case SCORE_DESCENDING:
          if (sortByScore(sortedListData, false) == false) return;
          break;
        default:
          break;
      }
    }
    sortedListData.add(0, null);
    this.listItemUsers = sortedListData;
    this.userList.setAcceptableValues(sortedListData);
  }

  /**
   * 現在選択可能な学生を、入力されているテキストからID、名前でフィルタリングした新しいリストを取得します。
   * 
   * @return フィルタリングされた新しいリスト
   */
  private List<UserProxy> filterStudentsByIdOrName() {
    final String inputIdText = this.userIdText.getText();
    final List<UserProxy> filteredListData;
    if (inputIdText != null && inputIdText.length() > 0) {
      filteredListData = new ArrayList<UserProxy>();
      for (UserProxy user : this.allUsers) {
        if (user.getAccount().getId().contains(inputIdText)) {
          filteredListData.add(user);
        } else if (user.getName() != null && user.getName().contains(inputIdText)) {
          filteredListData.add(user);
        }
      }
    } else {
      filteredListData = new ArrayList<UserProxy>(this.allUsers);
    }
    return filteredListData;
  }

  /**
   * 成績順にソートします。
   * 
   * @param users ユーザーリスト
   * @param isAscending trueならば昇順、falseならば降順
   * @return ソートできたらtrue,できなかったらfalse
   */
  private boolean sortByScore(List<UserProxy> users, final boolean isAscending) {
    if (this.userIdToRecord == null) {
      showErrorDialog("User records not loaded. Please wait a moment or reload."); //$NON-NLS-1$
      return false;
    }

    class UserComparatorByRecord implements Comparator<UserProxy> {

      Map<String, RecordProxy> map;
      boolean ascending;

      UserComparatorByRecord(Map<String, RecordProxy> map) {
        this.map = map;
        this.ascending = isAscending;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public int compare(UserProxy o1, UserProxy o2) {
        final RecordProxy r1 = this.map.get(o1.getAccount().getId());
        final RecordProxy r2 = this.map.get(o2.getAccount().getId());
        int toReturn = r1.getScore() > r2.getScore() ? 1 : r1.getScore() < r2.getScore() ? -1 : 0;
        return this.ascending ? toReturn : -toReturn;
      }

    }

    Collections.sort(users, new UserComparatorByRecord(this.userIdToRecord));

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearUserPage() {
    this.panel.clearScoreData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void highlightRow(LectureScore rowData) {
    this.panel.highlightRow(rowData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Messages messages = getClientFactory().getMessages();
    this.panel = new StudentPanel(messages, true);
    this.userInfoButton = createImageButton("taskit/userinfo32.png", "24pt"); //$NON-NLS-1$ //$NON-NLS-2$
    this.scoreButton = createImageButton("taskit/score32.png", "24pt"); //$NON-NLS-1$ //$NON-NLS-2$
    this.uncallButton = createImageButton("taskit/uncall32.png", "24pt"); //$NON-NLS-1$ //$NON-NLS-2$
    this.reloadButton = createImageButton("taskit/reload32.png", "24pt"); //$NON-NLS-1$ //$NON-NLS-2$
    this.userIdButton = createImageButton("taskit/keyboard32.png", "16pt"); //$NON-NLS-1$ //$NON-NLS-2$
    this.mapButton = createImageButton("taskit/map32.png", "24pt"); //$NON-NLS-1$//$NON-NLS-2$

    initUserList(messages);
    initSortTypeList();
    final Widget widget = binder.createAndBindUi(this);

    localizeMessages(messages);
    this.viewersLabel.setVisible(false);
    this.userIdText = new TextBox();
    listenUserIdFilterChange();

    return widget;
  }

  private static Image createImageButton(String imageUrl, String sizeInPt) {
    Image image = new Image(imageUrl);
    image.setSize(sizeInPt, sizeInPt);
    return image;
  }

  /**
   * ユーザーID入力テキストを監視し、入力があればフィルタリングを行うようイベント処理します。
   */
  private void listenUserIdFilterChange() {
    this.userIdText.addKeyUpHandler(new KeyUpHandler() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void onKeyUp(@SuppressWarnings("unused") KeyUpEvent event) {
        resetListData();

        final int selectableUserCount = listItemUsers.size() - 1; // 1つは初期選択用のnull
        // 絞り込んだ結果一人しか選択可能な生徒がいない場合
        if (listItemUsers != null && selectableUserCount == 1) {
          presenter.listSelectionChanged(listItemUsers.get(1));
        }
      }
    });
  }

  private void initSortTypeList() {
    this.sortTypeList = new ValueListBox<StudentListViewImpl.SortType>(new Renderer<SortType>() {

      @Override
      public String render(SortType object) {
        if (object == null) return ""; //$NON-NLS-1$
        final Messages messages = getClientFactory().getMessages();
        switch (object) {
          case ID_ASCENDING:
            return messages.sortTypeIdAscending();
          case ID_DESCENDING:
            return messages.sortTypeIdDescending();
          case SCORE_ASCENDING:
            return messages.sortTypeScoreAscending();
          case SCORE_DESCENDING:
            return messages.sortTypeScoreDescending();
          default:
            throw new IllegalStateException("Unknown sort type : " + object); //$NON-NLS-1$
        }
      }

      @Override
      public void render(SortType object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }
    });
    this.sortTypeList.setAcceptableValues(Arrays.asList(SortType.values()));
    this.sortTypeList.setValue(lastSortType, false);

    this.sortTypeList.addValueChangeHandler(new ValueChangeHandler<SortType>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void onValueChange(@SuppressWarnings("unused") ValueChangeEvent<SortType> event) {
        lastSortType = sortTypeList.getValue();
        resetListData();
      }
    });
  }

  private void initUserList(final Messages messages) {
    this.userList = new ValueListBox<UserProxy>(new Renderer<UserProxy>() {

      @Override
      public String render(UserProxy object) {
        if (object == null) return messages.selectIdLabel();
        StringBuilder sb = new StringBuilder(object.getAccount().getId());
        if (object.getName() != null) {
          sb.append(" " + object.getName()); //$NON-NLS-1$
        }
        return sb.toString();
      }

      @Override
      public void render(UserProxy object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }
    });
    this.userList.addValueChangeHandler(new ValueChangeHandler<UserProxy>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onValueChange(ValueChangeEvent<UserProxy> event) {
        StudentListViewImpl.this.presenter.listSelectionChanged(event.getValue());
      }
    });
  }

  private void localizeMessages(final Messages messages) {
    this.viewersLabel.setText(messages.viewersLabel() + ": "); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showUserPage(StudentwiseRecordModel model) {
    this.panel.showUserPage(model);
  }

  @UiHandler("uncallButton")
  void uncallButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.uncall(getSelectedUser().getAccount());
  }

  @UiHandler("reloadButton")
  void reloadUserPage(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.reloadUserPage();
  }

  @UiHandler("userIdButton")
  void userIdInputButtonPressed(ClickEvent evt) {
    showPopup(this.userIdText, evt.getClientX(), evt.getClientY());
    this.userIdText.setFocus(true);
  }

  @UiHandler("scoreButton")
  void scoreButtonPressed(ClickEvent evt) {
    final RecordProxy record = getCurrentUserRecord();
    if (record == null) return;

    final RecordView recordView = new RecordView(getClientFactory().getMessages());
    recordView.setRecord(record);
    showPopup(recordView, evt.getClientX(), evt.getClientY());
  }

  @UiHandler("userInfoButton")
  void userInfoButtonPressed(ClickEvent evt) {
    final UserProxy selectedUser = getSelectedUser();
    if (selectedUser == null) return;

    final UserInfoView userInfoView = new UserInfoView(getClientFactory().getMessages());
    userInfoView.setUser(selectedUser);
    showPopup(userInfoView, evt.getClientX(), evt.getClientY());
  }

  @UiHandler("mapButton")
  void mapButtonPressed(ClickEvent evt) {
    final UserProxy selectedUser = getSelectedUser();
    if (selectedUser == null) return;
    final Image mapImage = new Image("roommap?id=" + selectedUser.getAccount().getId()); //$NON-NLS-1$

    showPopup(mapImage, evt.getClientX(), evt.getClientY());
  }

  private static void showPopup(Widget widget, int x, int y) {
    final PopupPanel popup = new PopupPanel(true);
    popup.add(widget);
    popup.setPopupPosition(x, y);
    popup.show();
  }

  private RecordProxy getCurrentUserRecord() {
    final UserProxy selectedUser = getSelectedUser();
    if (selectedUser == null) return null;
    return this.userIdToRecord.get(selectedUser.getAccount().getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserProxy getSelectedUser() {
    return this.userList.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRecords(Map<String, RecordProxy> records) {
    this.userIdToRecord = records;
  }

  /**
   * {@inheritDoc}true ;
   */
  @Override
  public void setUncallable(boolean uncallable) {
    this.uncallButton.setVisible(uncallable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setViewers(List<String> viewers) {
    if (viewers.size() == 0) {
      this.viewersLabel.setVisible(false);
      this.viewers.setText(""); //$NON-NLS-1$
      return;
    }
    this.viewersLabel.setVisible(true);

    StringBuilder sb = new StringBuilder();
    for (String viewerId : viewers) {
      if (sb.length() > 0) sb.append(","); //$NON-NLS-1$
      sb.append(viewerId);
    }
    this.viewers.setText(sb.toString());
  }

}
