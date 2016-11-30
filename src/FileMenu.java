import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.event.EventHandler;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;


public class FileMenu extends MenuBar {

	Controller controller;

	/**
	 * Sets actions for items in the file menu
	 * @param c - reference to the controller
	 */
	public FileMenu(Controller c) {

		controller = c;

		final Menu menuItem1 = new Menu("File");
		MenuItem newSpace = new MenuItem("New");
		MenuItem print = new MenuItem("Print");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("Save As...");
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
				controller.confirmDialog(false);
				controller.clear();
			}
		});
		
		//exit program
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				controller.confirmDialog(true);	
				Platform.exit();
			}
		});
		
		//save current workspace
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					controller.save(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		saveAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					controller.save(true);
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
					controller.confirmDialog(false);	
					controller.open();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		getMenus().add(menuItem1);
		menuItem1.getItems().addAll(newSpace, new SeparatorMenuItem(), print, new SeparatorMenuItem(), save, saveAs, open, new SeparatorMenuItem(), exit );

		getStyleClass().add("menu");
	}
}
