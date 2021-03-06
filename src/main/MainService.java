package main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import room.choice.RoomChoiceController;

public class MainService {
	private MainController mainController;

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void rcOpen() { // roomChoice메뉴를 실행하는 메소드
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/room/choice/rcForm.fxml"));

		Parent roomChoiceForm;

		try {

			roomChoiceForm = loader.load();

			mainController.setRoomChoiceForm(roomChoiceForm);// rcForm의 정보저장

			RoomChoiceController roomChoiceController = loader.getController();
			mainController.setRoomChoiceController(roomChoiceController);

			Scene scene = new Scene(roomChoiceForm);

			Stage stage = new Stage();
			stage.setTitle("방선택");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void regOpen() { // Register메뉴를 실행하는 메소드
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/register/regForm.fxml"));

		Parent regForm;

		try {
			regForm = loader.load();

			mainController.setRegForm(regForm); // regForm의 정보저장
			mainController.setRegController(loader.getController());

			Scene scene = new Scene(regForm);

			Stage stage = new Stage();
			stage.setTitle("회원가입");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void adminOpen() {// admin메뉴를 실행하는 메소드
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AdminForm.fxml"));

		Parent adminForm;
		try {
			adminForm = loader.load();

			mainController.setAdminForm(adminForm);// adminForm의 정보저장
			mainController.setAdminController(loader.getController());

			Scene scene = new Scene(adminForm);

			Stage stage = new Stage();
			stage.setTitle("관리자 모드");
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
