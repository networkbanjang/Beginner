package song;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import song.remotecontrol.RemoteControlController;
import song.remotecontrol.RemoteControlService;
import song.search.SongSearchController;

public class SongService {
	private SongController songController;
	private RemoteControlService remoteService;
	private SongDAO songDao;

	public SongService() {
		songDao = new SongDAO();
	}
	
	public RemoteControlService getRemoteService() {
		return remoteService;
	}


	public void setRemoteService(RemoteControlService remoteService) {
		this.remoteService = remoteService;
	}


	public SongController getSongController() {
		return songController;
	}
	
	public void setSongController(SongController songController) {
		this.songController = songController;
	}
	
	// 노래 리스트 창 오픈 메소드
	public void songSearchOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/song/search/titleSongSearch.fxml"));
		Parent songSearchForm;
		
		try {
			remoteService.closeForm();
			
			songSearchForm = loader.load();
			
			SongSearchController searchController = loader.getController();
			songController.setSongSearchController(searchController);
			searchController.setSearchForm(songSearchForm);
			searchController.setSongController(songController);
			songController.setSongSearchForm(songSearchForm);
			// 검색창 꺼지게 remoteController 에 searchForm 설정
			songController.getRemoteController().setSearchController(searchController);
			
			
			
			Scene scene = new Scene(songSearchForm);
			
			Stage nowStage = (Stage) songController.getSongForm().getScene().getWindow();
			Double SongX = nowStage.getX();
			Double SongY = nowStage.getY();
			
			Stage stage = new Stage();
			stage.setTitle("노래 검색");
			stage.setX(SongX + 1005);
			stage.setY(SongY);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// DB내 카운트 추가
	public SongDTO songPlay(String songNumber) {
		// DB내 카운트 추가
		SongDTO songDto = new SongDTO();
		songDto = songDao.selectNum(songNumber);
		songDao.addCount(songDto);
//		System.out.println(songDto);
		return songDto;
	}

	// 방 예약으로 변경 : 예약가능 -> 예약중(0 -> 1)
	public void roomReserve(String room) {
		songDao.roomReserve(room);
		
	}
	
	// 방 예약가능으로 변경 : 예약중 -> 예약가능(1 -> 0)
	public void roomAvailable(String room) {
		songDao.roomAvailable(room);
	}
	
	
	
}
