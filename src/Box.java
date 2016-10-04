import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Box extends Rectangle {
	Controller controller;

	public Box(int x, int y, int h, int w, Controller c) {
		super(x, y, h, w);
		
		controller = c;
		
		setFill(null);
		//css ID, should be changed to class
		setId("rect");
		Box thisBox = this;
		
		
		//box corner jumps to cursor on drag, need to fix that
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//css style to show grid while rectangle is being dragged - should be call to controller like below on setOnMouseReleased
				controller.workspace.getStyleClass().remove("noGrid");
				controller.workspace.getStyleClass().add("grid");
				double x = event.getX();
				double y = event.getY();
				//round to nearest 20 px
				setX(Math.floorDiv((int) x, 20) * 20);
				setY(Math.floorDiv((int) y, 20) * 20);
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				controller.showGrid();			
			}
		});
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.selectBox(thisBox);
				if (controller.isAddingRelation()) {
					controller.endCurrentRelation();
				}
				//consume keeps event from interacting with elements below
				event.consume();
			}
		});
	}	
	
}
