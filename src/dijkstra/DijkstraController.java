package dijkstra;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DijkstraController extends Application
{
	public static Stage homeStage;

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private AnchorPane anchor;

	@FXML
	private Button btnFindPath;
	
    @FXML
    private Button btnReset;
    
    @FXML
    private TextField fldWidth;

    @FXML
    private TextField fldHeight;

	private Stage prime;
	private DijkstraController controller;
	private GridPane gridPane;
	private Dijkstra d;
	private int width, height;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/dijkstra/DijkstraFX.fxml"));
			anchor = loader.load();
			controller = loader.getController();
			controller.setMatrix(8,8);
			Scene scene = new Scene(anchor);
			prime = primaryStage;
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

	public void setMatrix(int width, int height)
	{
		this.width = width;
		this.height = height;
		d = new Dijkstra(width, height);
		gridPane = new GridPane();
		anchor.getChildren().add(gridPane);
		d.setStart(2, 1);
		d.setEnd(7, 7);
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
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
		GridPane grid = new GridPane();
		anchor.getChildren().add(grid);
		d.setStart(2, 1);
		d.setEnd(7, 7);
		for (int i = 0; i < this.width; i++)
		{
			for (int j = 0; j < this.height; j++)
			{
				Button b = new Button(d.get(i, j));
				b.setFont(new Font("Calibri", 22));
				b.setPrefSize(45, 45);
				grid.add(b, i, j);
			}
		}
	}
	
    @FXML
    void reset(ActionEvent event) {
    	int width = Integer.parseInt(fldWidth.getText());
    	int height = Integer.parseInt(fldHeight.getText());
    	setMatrix(width, height);
    }

}
