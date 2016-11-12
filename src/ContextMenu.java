import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;

/**
 * ContextMenu Class
 * Toolbar with buttons that change according to the currently selected object
 * -Currently has three states - Box selected, Relation selected, nothing selected
 */
public class ContextMenu extends VBox {

	Controller controller;
	ImageView delete;
	ImageView addBox;
	ImageView addRelation;
        
    Image imageDelete = new Image(getClass().getResourceAsStream("/ui elements/delete.png"), 60, 60, true, true);
    Image imageAddBox = new Image(getClass().getResourceAsStream("/ui elements/addBox.png"), 60, 60, true, true);
    Image imageAddRelation = new Image(getClass().getResourceAsStream("/ui elements/addRelation.png"), 60, 60, true, true);
    Image imageAggrigation = new Image(getClass().getResourceAsStream("/ui elements/aggregation.png"), 60, 60, true, true);
    Image imageComposion = new Image(getClass().getResourceAsStream("/ui elements/composition.png"), 60, 60, true, true);
    Image imageDependency = new Image(getClass().getResourceAsStream("/ui elements/association.png"), 60, 60, true, true);
    Image imageGeneralization = new Image(getClass().getResourceAsStream("/ui elements/generalization.png"), 60, 60, true, true);
    Image imageSolidLine = new Image(getClass().getResourceAsStream("/ui elements/solidLine.png"), 60, 60, true, true);
    Image imageDottedLine = new Image(getClass().getResourceAsStream("/ui elements/dottedLine.png"), 60, 60, true, true);

    /**
     * ContextMenu constructor
     * @param c - the Controller
     */
	public ContextMenu(Controller c) {
		controller = c;
		
		//space between buttons
		setSpacing(10);
		setPadding(new Insets(20, 10, 10, 10));
		//preferred width - need to unify button widths and this won't be an issue
		setPrefWidth(80);

		addBox = new ImageView(imageAddBox);
		addRelation = new ImageView(imageAddRelation);
		delete = new ImageView(imageDelete);
		Tooltip.install(addBox, new Tooltip("Add Class Box"));
		Tooltip.install(addRelation, new Tooltip("Add Relation"));
		Tooltip.install(delete, new Tooltip("Delete"));
		
		getChildren().add(addBox);
		getStyleClass().add("vbox");

		addBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.deselectBox();
				Box rect = new Box(controller);
				controller.cancelCurrentRelation();
			}
		});
		
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.deleteSelected();
				controller.cancelCurrentRelation();
			}
		});

		//only available when a box is selected
		addRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.startNewRelation();
			}
		});
	}

	/**
	 * Displays the delete button
	 */
	public void showDeleteButton() {
		getChildren().add(delete);
	}
	
	/**
	 * Hides the delete button
	 */
	public void hideDeleteButton() {
		getChildren().remove(delete);
	}
	
	/**
	 * Displays the add box button
	 */
	public void showAddBoxButton() {
		getChildren().add(addBox);
	}
	
	/**
	 * Hides the add box button
	 */
	public void hideAddBoxButton() {
		getChildren().remove(addBox);
	}

	/**
	 * Displays the add relation button
	 */
	public void showAddRelationButton() {
		getChildren().add(addRelation);
	}
	
	/**
	 * Hides the add relation button
	 */
	public void hideAddRelationButton() {
		getChildren().remove(addRelation);
	}
	
	/**
	 * applies a shadow or removes a shadow from the addRelation button
	 * @param b - boolean for whether a shadow should be applied or removed
	 */
	public void setAddRelationShadow(boolean b) {
		if (b) {
			addRelation.setEffect(new DropShadow(20, Color.LIGHTGREY));
		} else {
			addRelation.setEffect(null);
		}
	}

}