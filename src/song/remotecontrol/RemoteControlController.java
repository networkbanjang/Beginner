package song.remotecontrol;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import common.CommonService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import song.SongController;
import song.SongDTO;
import song.search.PopularChartController;
import song.search.SongSearchController;
import song.search.SongSearchDTO;
import song.search.songSearchDAO;

public class RemoteControlController implements Initializable {
	@FXML private Button one;
	@FXML private Button two;
	@FXML private Button three;
	@FXML private Button four;
	@FXML private Button five;
	@FXML private Button six;
	@FXML private Button seven;
	@FXML private Button eight;
	@FXML private Button nine;
	@FXML private Button zero;

	@FXML private Button reserve;
	@FXML private Button primaryReserve;
	@FXML private Button start;

	@FXML private Button popularChart;
	@FXML private Button titleSearch;
	@FXML private Button singerSearch;
	
	@FXML private Button pause;
	@FXML private Button maidJump;
	@FXML private Button clap;
	
	@FXML private Label testLabel;
	
	private SongController songController;
	private RemoteControlController remoteController; 
	private PopularChartController chartController;
	private SongSearchController searchController;
	
	private RemoteControlService remoteService;
	
	private Parent remoteForm;
	private Parent songForm;
	private Parent searchForm;
	
	private SongSearchDTO searchDTO;
	private SongDTO songDTO;
	private songSearchDAO searchDAO;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		remoteService = new RemoteControlService();
		remoteService.setRemoteController(this);
		searchDAO = new songSearchDAO();
	}

	
	public RemoteControlService getRemoteService() {
		return remoteService;
	}


	public void setRemoteService(RemoteControlService remoteService) {
		this.remoteService = remoteService;
	}


	public RemoteControlController getRemoteController() {
		return remoteController;
	}


	public void setRemoteController(RemoteControlController remoteController) {
		this.remoteController = remoteController;
	}


	public PopularChartController getChartController() {
		return chartController;
	}


	public void setChartController(PopularChartController chartController) {
		this.chartController = chartController;
	}


	public SongSearchController getSearchController() {
		return searchController;
	}


	public void setSearchController(SongSearchController searchController) {
		this.searchController = searchController;
	}


	public Parent getSongForm() {
		return songForm;
	}


	public void setSongForm(Parent songForm) {
		this.songForm = songForm;
	}


	public Parent getRemoteForm() {
		return remoteForm;
	}


	public void setRemoteForm(Parent remoteForm) {
		this.remoteForm = remoteForm;
	}



	public SongController getSongController() {
		return songController;
	}


	public void setSongController(SongController songController) {
		this.songController = songController;
	}

	public void one() {
		inputNum("1");
	}
	public void two() {
		inputNum("2");
	}
	public void three() {
		inputNum("3");
	}
	public void four() {
		inputNum("4");
	}
	public void five() {
		inputNum("5");
	}
	public void six() {
		inputNum("6");
	}
	public void seven() {
		inputNum("7");
	}
	public void eight() {
		inputNum("8");
	}
	public void nine() {
		inputNum("9");
	}
	public void zero() {
		inputNum("0");
	}
	
	public void inputNum(String num) {
		
		// ????????? ?????????
		sound1();
		
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		String songNum;
		// ????????? ?????? ????????? ?????????
		if (searchDTO != null) {
			songNum = searchDTO.getSongNum() + num;
			// ????????? ????????? 6????????? ?????? ????????????
			if (songNum.length() <= 6) {
				resNum.setText(songNum);
			}
			searchDTO = null;
			
		} else {
			songNum = resNum.getText() + num;
			searchDTO = searchDAO.findSong(songNum);
			
			reserveOpacityOne();
			
			// ?????? ????????? ????????? ?????????
			if (searchDTO != null ) {
				resNum.setText(songNum + " - " + searchDTO.getSongTitle() + " (" + searchDTO.getSongSinger() + ")");
			} else {
				if (songNum.length() <= 6) {
					resNum.setText(songNum);
				}
			}
			
		}
		
	}
	
	
	public void cancel() {
		// ????????? ?????????
		sound1();
		
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		if (resNum.getText().equals("")) {
			songController.songCancelProc();
		} else {
			reserveOpacityZero();
			resNum.setText("");
			
		}
		// ????????? ????????????????????? ????????? null????????? ?????????
		if (searchDTO != null) {
			searchDTO = null;
		}
		
		
	}
	public void start() {
		// ????????? ?????????
		sound1();
		
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		resNumTitle.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");
		resNum.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");
		
		// ?????? ????????? ?????? ?????????
		if (resNum.getText().equals("")) {
			if (songController.getSongNumber().size() == 0) {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			} else {
				songController.songStartProc();
			}
		} else {
			resNum.setText("");
			// ?????? ?????? ?????? ????????? ?????????
			if (searchDTO != null) {
				SongDTO songdto = new SongDTO();
				songdto.setSongNum(searchDTO.getSongNum());
				songdto.setSongCount(searchDTO.getSongCount());
				songdto.setSongLink(searchDTO.getSongLink());
				
				// ????????? ???????????????
				MediaPlayer mediaPlayer = songController.getMediaPlayer();
				if (mediaPlayer != null && !mediaPlayer.getStatus().toString().equals("STOPPED")) {
					CommonService.msg("?????? ????????? ??????????????????.");
					
					
				// ????????? ???????????? ?????????
				} else {
					songController.primaryReserveSong(songdto);
					songController.songStartProc();
				}
				searchDTO = null;
				
			} else {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			}
				
		}
			
	}
	
	public void reserve() {
		// ????????? ?????????
		sound1();
		
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		resNumTitle.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");
		resNum.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");

		// ?????? ????????? ?????? ?????????
		if (resNum.getText().equals("")) {
			// searchForm??? ?????? ?????? ?????? ???????????????
			if (searchController.getSearchDTO() != null) {
				SongDTO songdto = new SongDTO();
				songdto.setSongNum(searchController.getSearchDTO().getSongNum());
				songdto.setSongCount(searchController.getSearchDTO().getSongCount());
				songdto.setSongLink(searchController.getSearchDTO().getSongLink());
				songController.reserveSong(songdto);
				
			} else {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			}
		} else {
			resNum.setText("");
			// ?????? ?????? ?????? ????????? ?????????
			if (searchDTO != null) {
				SongDTO songdto = new SongDTO();
				songdto.setSongNum(searchDTO.getSongNum());
				songdto.setSongCount(searchDTO.getSongCount());
				songdto.setSongLink(searchDTO.getSongLink());
				songController.reserveSong(songdto);
				searchDTO = null;
				
			} else {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			}
						
		}
		
	}
	
	public void primaryReserve() {
		// ????????? ?????????
		sound1();
		
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");

		reserveOpacityZero();
		// ?????? ????????? ?????? ?????????
		if (resNum.getText().equals("")) {
			// searchForm??? ?????? ?????? ?????? ???????????????
			if (searchController.getSearchDTO() != null) {
				SongDTO songdto = new SongDTO();
				songdto.setSongNum(searchController.getSearchDTO().getSongNum());
				songdto.setSongCount(searchController.getSearchDTO().getSongCount());
				songdto.setSongLink(searchController.getSearchDTO().getSongLink());
				songController.primaryReserveSong(songdto);
				
			} else {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			}
			
		} else {
			resNum.setText("");
			// ?????? ?????? ?????? ????????? ?????????
			if (searchDTO != null) {
				SongDTO songdto = new SongDTO();
				songdto.setSongNum(searchDTO.getSongNum());
				songdto.setSongCount(searchDTO.getSongCount());
				songdto.setSongLink(searchDTO.getSongLink());
				songController.primaryReserveSong(songdto);
				searchDTO = null;
				
			} else {
				CommonService.msg("?????? ????????? ????????? ????????????.");
			}
						
		}
	}
	
	public void cancelReserve() {
		// ????????? ?????????
		sound1();
		
		songController.cancelReserve();
	}
	
	public void popularChart() {
		// ????????? ?????????
		sound1();
		
		remoteService.popularChart();
	}
	
	public void titleSearch() {
		// ????????? ?????????
		sound1();
		
		remoteService.titleSearch();
	}
	
	public void singerSearch() {
		// ????????? ?????????
		sound1();
		
		remoteService.singerSearch();
	}
	
	public void pause() {
		// ????????? ??????????????? ???????????? ????????? ???????????? ?????????
		if (songController.getMediaPlayer() == null) return;
		
		if (pause.getText().equals("????????????")) {
			songController.pause();
			pause.setText("??????????????????");
			Font font = Font.loadFont("file:src/font/NanumBarunGothicBold.otf", 16);
			pause.setFont(font);
			
		} else if (pause.getText().equals("??????????????????")) {
			songController.pauseCancel();
			pause.setText("????????????");
			Font font = Font.loadFont("file:src/font/NanumBarunGothicBold.otf", 20);
			pause.setFont(font);
		}
		
	}
	
	public void madijump() {
		songController.madiJump();
		
	}
	
	public void clap() {
		try {
			File clapEffect = new File("src/song/remotecontrol/clap.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(clapEffect));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sound1() {
		// ????????? ?????????
		if (songController.getMediaPlayer() == null || songController.getMediaPlayer().getStatus().toString().equals("STOPPED"))
			CommonService.sound1();
	}
	
	public void reserveOpacityZero() {
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		resNumTitle.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");
		resNum.setStyle("-fx-background-color: #ffffff; -fx-opacity : 0");
		Font font = Font.loadFont("file:src/font/NanumBarunGothicBold.otf", 20);
		resNumTitle.setFont(font);
		resNum.setFont(font);
	}

	public void reserveOpacityOne() {
		Label resNumTitle = (Label) songController.getSongForm().lookup("#resNumTitle");
		Label resNum = (Label) songController.getSongForm().lookup("#resNum");
		resNumTitle.setStyle("-fx-background-color: #ffffff; -fx-opacity : 1");
		resNum.setStyle("-fx-background-color: #ffffff; -fx-opacity : 1");
		Font font = Font.loadFont("file:src/font/NanumBarunGothicBold.otf", 20);
		resNumTitle.setFont(font);
		resNum.setFont(font);
	}
}