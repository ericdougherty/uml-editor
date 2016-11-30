import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("UML Editor - fiVe");
        primaryStage.setMaximized(true);	
		Controller controller = new Controller();	
		Platform.setImplicitExit(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				controller.confirmDialog(true);	
			}
		});
		Scene scene = new Scene(controller.ui );
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}