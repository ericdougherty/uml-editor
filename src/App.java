import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("UML Editor");
        primaryStage.setMaximized(true);
		
		Controller controller = new Controller();		

		Scene scene = new Scene(controller.ui );
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/ui elements/logo.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}