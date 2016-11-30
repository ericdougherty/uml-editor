import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.event.EventHandler;

import java.io.IOException;

import javafx.event.ActionEvent;

/**
 * FileMenu Class Currently just a visual placeholder - no functionality
 */
public class FileMenu extends MenuBar {

	Controller controller;

	/**
	 * Sets actions for items in the file menu
	 * @param c - reference to the controller
	 */
	public FileMenu(Controller c) {

		controller = c;

		final Menu menuItem1 = new Menu("File");
		final Menu menuItem2 = new Menu("Preferences");
		final Menu menuItem3 = new Menu("Help");
		MenuItem newSpace = new MenuItem("New");
		MenuItem print = new MenuItem("Print");
		MenuItem save = new MenuItem("Save");
		MenuItem open = new MenuItem("Open");
		MenuItem exit = new MenuItem("Exit");

		//print workspace
		print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				controller.print();
			}
		});
		
		//clear workspace
		newSpace.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				controller.clear();
			}
		});
		
		//exit program
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		
		//save current workspace
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					controller.save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//open valid uml file
		open.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					controller.open();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		getMenus().addAll(menuItem1, menuItem2, menuItem3);
		menuItem1.getItems().addAll(newSpace, print, save, open, exit );

		getStyleClass().add("menu");
	}
}
