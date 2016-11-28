import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * WorkSpace Class
 * The WorkSpace holds the editable nodes where the user can interact
 * -Has a CSS overlay that shows/hides grid for placement
 */
public class WorkSpace extends Pane{
	
	Controller controller;
	
	/**
	 * WorkSpace constructor
	 * @param c - the Controller
	 */
	public WorkSpace(Controller c) {
		
		controller = c;
		WorkSpace workspace = this;
		//setMinWidth(1300);
		//setMinHeight(700);
		getStyleClass().add("noGrid");
		
		//Deselects all objects
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				workspace.requestFocus();
				controller.deselectRelation();
				controller.deselectBox();
				controller.toolbar.setAddRelationShadow(false);
				if (controller.isAddingRelation()) {
					controller.displayInvalidRelationMessage();
				}
			}
		});
		
		setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (controller.isAddingRelation()) {
					controller.setCurrentRelationEndPosition(event.getX(), event.getY());
				}
			}
		});
	}

}
