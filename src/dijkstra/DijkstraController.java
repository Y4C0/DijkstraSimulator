package dijkstra;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DijkstraController extends Application
{
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

	private Stage prime;
	private DijkstraController controller;
	private GridPane gridPane;
	private Dijkstra d;
	private int size;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	public void setMatrix(int size)
	{
		this.size = size;
		d = new Dijkstra(size, size);
		gridPane = new GridPane();
		anchor.getChildren().add(gridPane);
		d.setStart(2, 1);
		d.setEnd(7, 7);
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				Button b = new Button(d.get(i, j));
				b.setFont(new Font("Calibri", 22));
				b.setPrefSize(45, 45);
				gridPane.add(b, i, j);
			}
		}
	}

	@FXML
	void findPath(ActionEvent event)
	{
		d.findPath();
		gridPane.getChildren().clear();
		d.setStart(2, 1);
		d.setEnd(7, 7);
		for (int i = 0; i < this.size; i++)
		{
			for (int j = 0; j < this.size; j++)
			{
				if (d.get(i, j).equals("N"))
				{
					Button c = new Button("");
					c.setFont(new Font("Calibri", 22));
					c.setPrefSize(45, 45);
					c.setStyle("-fx-background-color: white;");
					gridPane.add(c, i, j);
				} else
				{
					Button b = new Button(d.get(i, j));
					b.setFont(new Font("Calibri", 22));
					b.setPrefSize(45, 45);
					gridPane.add(b, i, j);
				}
			}
		}
	}

	class Reset implements EventHandler<ActionEvent>
	{
		private DijkstraController controller;

		public Reset(DijkstraController controller)
		{
			this.controller = controller; // Get controller after reset
		}

		@Override
		public void handle(ActionEvent event)
		{
			int size = Integer.parseInt(controller.fldSize.getText());

			if (size <= 0)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Size must be bigger than 0.");
				Image image = new Image("dijkstra/sad.png");
				ImageView imageView = new ImageView(image);
				alert.setGraphic(imageView);
				alert.show();
			} else
			{
				controller.gridPane.getChildren().clear();
				prime.setHeight(size * 45 + 90);
				prime.setWidth(size * 45);
				controller.setMatrix(size);
			}
		}
	}
}
