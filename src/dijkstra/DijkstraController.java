package dijkstra;

import com.sun.tools.javac.code.Flags.Flag;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DijkstraController extends Application {
	public static Stage homeStage;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private StackPane anchor;

	@FXML
	private Button btnFindPath;

	@FXML
	private Button btnReset;

	@FXML
	private TextField fldSize;

	@FXML
	private Button btnStart;

	@FXML
	private Button btnEnd;

	@FXML
	private VBox menu;

	@FXML
	private Label instructions;

	@FXML
	private CheckBox checkBox;

	private Stage prime;
	private DijkstraController controller;
	private GridPane gridPane;
	private Dijkstra d;
	private int size;
	private Button buttons[][];

	private enum flags {
		DEFAULT, START, END, WALL;
	}

	private flags setFlag = flags.START;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dijkstra/DijkstraFX.fxml"));
			mainAnchor = loader.load();
			controller = loader.getController();
			controller.setMatrix(Integer.parseInt(controller.fldSize.getText()));
			Scene scene = new Scene(mainAnchor);
			prime = primaryStage;
			Button reset = controller.btnReset;
			reset.setOnAction(new Reset(controller));
			scene.getStylesheets().add(getClass().getResource("/dijkstra/style.css").toExternalForm());
			prime.setTitle("Dijkstra");
			prime.setScene(scene);
//			prime.getIcons().add(new Image("mines/icon.png"));
			prime.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setMatrix(int size) {
		buttons = new Button[size][size];
		this.size = size;
		d = new Dijkstra(size, size);
		gridPane = new GridPane();
		buttons = new Button[size][size];
		anchor.getChildren().add(gridPane);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Button b = makeButton(i, j, size);
				gridPane.add(b, i, j);
			}
		}
	}

	@FXML
	void findPath(ActionEvent event) {
		if (checkBox.isSelected())
			d.setDiagonalWaysTrue();
		else
			d.setDiagonalWaysFalse();
		if (!setFlag.equals(flags.DEFAULT))
			alert("Error", "You must choose start and end nodes.");
		else {
			d.findPath();
			gridPane.getChildren().clear();
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					if (d.get(i, j).equals("N")) {
						Button c = new Button("");
						c.setFont(new Font("Calibri", 22));
						c.setPrefSize(45, 45);
						c.setStyle("-fx-background-color: white;");
						gridPane.add(c, i, j);
					} else if (d.get(i, j).equals("S") || d.get(i, j).equals("E")) {
						Button c;
						if (d.get(i, j).equals("S"))
							c = new Button("S");
						else
							c = new Button("E");
						c.setFont(new Font("Calibri", 22));
						c.setPrefSize(45, 45);
						c.setStyle("-fx-background-color: white;");
						gridPane.add(c, i, j);
					} else if (d.get(i, j).equals("*")) {
						Button b = new Button("*");
						b.setFont(new Font("Calibri", 22));
						b.setPrefSize(45, 45);
						gridPane.add(b, i, j);
					} else {
						Button b = new Button(d.get(i, j));
						b.setFont(new Font("Calibri", 22));
						b.setPrefSize(45, 45);
						gridPane.add(b, i, j);
					}
				}
			}
			if (d.getNode(d.getEndX(), d.getEndY()).dist == Integer.MAX_VALUE)
				instructions.setText("No way has been found!");
			else
				instructions.setText("The shortest way has been found!");
		}
	}

	class Reset implements EventHandler<ActionEvent> {
		private DijkstraController controller;

		public Reset(DijkstraController controller) {
			this.controller = controller; // Get controller after reset
		}

		@Override
		public void handle(ActionEvent event) {
			int size = Integer.parseInt(controller.fldSize.getText());

			if (size <= 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Size must be bigger than 0.");
				Image image = new Image("dijkstra/sad.png");
				ImageView imageView = new ImageView(image);
				alert.setGraphic(imageView);
				alert.show();
			} else {
				controller.gridPane.getChildren().clear();
//				controller.menu.setPrefWidth(size * 40);
				controller.menu.setAlignment(Pos.CENTER);
				prime.setHeight(size * 45 + 115);
				prime.setWidth(size * 45);
				controller.setFlag = flags.START;
				controller.instructions.setText("Choose start vertex.");
				controller.setMatrix(size);
			}
		}
	}

	private Button makeButton(int i, int j, int size) {
		buttons[i][j] = new Button(d.get(i, j));
		buttons[i][j].setFont(new Font("Calibri", 22));
		buttons[i][j].setPrefSize(45, 45);
		buttons[i][j].setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY)
				if (setFlag.equals(flags.START)) {
					d.setStart(i, j);
					buttons[i][j].setText("S");
					buttons[i][j].setStyle("-fx-background-color: white;");
					setFlag = flags.END;
					instructions.setText("Choose end vertex.");
				} else if (setFlag.equals(flags.END)) {
					d.setEnd(i, j);
					buttons[i][j].setText("E");
					buttons[i][j].setStyle("-fx-background-color: white;");
					setFlag = flags.DEFAULT;
					instructions.setText("Click on \"Find path\" button.");
				}
			if (event.getButton() == MouseButton.SECONDARY && setFlag.equals(flags.DEFAULT)) {
				if (!(d.get(i, j).equals("S") || d.get(i, j).equals("E"))) {
					if (!d.get(i, j).equals("*")) {
						buttons[i][j].setText("*");
						d.setWall(i, j);
					} else {
						buttons[i][j].setText(String.valueOf(d.getNode(i, j).getWeight()));
						d.removeWall(i, j);
					}
				}
			}
		});

		return buttons[i][j];
	}

	public void alert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}
}
