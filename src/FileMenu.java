import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;



public class FileMenu extends MenuBar {
	
	public FileMenu(){
		
		final Menu menuItem1 = new Menu("File");		
		final Menu menuItem2 = new Menu("Preferences");
		final Menu menuItem3 = new Menu("Help");
		MenuItem exit = new MenuItem("Exit");
                
                exit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        System.exit(0);
                    }
                });
                
		getMenus().addAll(menuItem1, menuItem2, menuItem3);
                menuItem1.getItems().addAll(exit);
		getStyleClass().add("menu");
                
                
	}

}
