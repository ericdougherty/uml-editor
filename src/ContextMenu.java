import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ContextMenu extends VBox {

	Controller controller;
	Button deleteBox;
	Button addBox;
	Button addRelation;

	public ContextMenu(Controller c) {
		super();
		
		controller = c;

		setSpacing(10);
		setPadding(new Insets(20, 10, 10, 10));

		addBox = new Button("Create Box");

		addBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Box rect = new Box(20, 20, 141, 241, controller);
				controller.workspace.getChildren().add(rect);

			}
		});
		
		deleteBox = new Button("Delete Box");

		deleteBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.deleteSelected();
			}
		});
		
		addRelation = new Button("Add Relation");

		addRelation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.startNewRelation();
			}
		});

		getChildren().add(addBox);
		getStyleClass().add("vbox");
	}

	public void showDeleteButton() {
		getChildren().add(deleteBox);
	}
	
	public void hideDeleteButton() {
		getChildren().remove(deleteBox);
	}

	public void showAddRelationButton() {
		getChildren().add(addRelation);
	}
	
	public void hideAddRelationButton() {
		getChildren().remove(addRelation);
	}

}
