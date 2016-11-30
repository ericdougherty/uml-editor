import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Box Class
 * Contains four Sections, can be connected by Relations
 * -Has two states, selected and unselected, though this is tracked by controller, not individual boxes
 */
public class Box extends VBox {
	Controller controller;
	private Section[] sections = {new Section(this, "add class name", true), new Section(this, "add attribute", false), new Section(this, "add operation", false), new Section(this, "add miscellaneous", false)};
	private Double offsetX;
	private Double offsetY;
	private int id;
	int previousx = 0;
	int previousy = 0;

	/**
	 * Box constructor
	 * Boxes are initialized with event handlers for mousedowns, click-and-drags, and clicks
	 * @param c - the Controller
	 */
	public Box(Controller c) {
		controller = c;
		
		getStyleClass().add("box");
		Box thisBox = this;
		setPrefWidth(141);
		
		getChildren().addAll(sections[0], sections[1], sections[2], sections[3]);
		
		//Enables click and drag of the boxes for movement
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.showGrid();
				double x = event.getSceneX() - offsetX;
				double y = event.getSceneY() - offsetY;
				if ((x < 0) || (y < 0)) {
					x = previousx;
					y = previousy;
				}
				if ((x + thisBox.getWidth()) > controller.workspace.getWidth()) {
					controller.workspace.setMinWidth(x + thisBox.getWidth());
			        controller.scrollpane.setHvalue(controller.scrollpane.getHmax()); 
				}
				if ((y + thisBox.getHeight()) > controller.workspace.getHeight()) {
					controller.workspace.setMinHeight(y + thisBox.getHeight());
			        controller.scrollpane.setVvalue(controller.scrollpane.getVmax()); 
				}
				previousx = Math.floorDiv((int) x, 20) * 20;
				previousy = Math.floorDiv((int) y, 20) * 20;
				//round to nearest 20 px
				relocate(Math.floorDiv((int) x, 20) * 20, Math.floorDiv((int) y, 20) * 20);
				controller.updateRelations();
				controller.changesMade();
			}
		});
		
		//Tracks the position of the mouse with relation to the box
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				offsetX = event.getSceneX() - getLayoutX();
				offsetY = event.getSceneY() - getLayoutY();
			}
		});
		
		//Hides the grid when the box is not being moved
		setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				controller.hideGrid();
			}
		});
		
		//Handles selecting the box or adding relation lines on click
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {				
				
				if (controller.isAddingRelation()) {
					controller.endCurrentRelation(thisBox);
				}
				else if (thisBox != controller.getSelectedBox()) {
					controller.deselectBox();
					controller.selectBox(thisBox);
				}
				//consume keeps event from interacting with elements below
				event.consume();
			}
		});
		
		//created box starts selected
		controller.addBox(this);
		controller.selectBox(this);
		
		//listener to update relations when height of box is changed
		heightProperty().addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				controller.updateRelations();
			}
	    });
		controller.changesMade();
	}
	
	/**
	 * Called when the box is deselected
	 * Handles setting the state of each section
	 */
	public void deselect() {
		boolean okayToHide = true;
		for (int i = 3; i >= 1; --i){
			sections[i].deselect();
			if (okayToHide && sections[i].isEmpty()) {
				getChildren().remove(sections[i]);
			}
			else {
				okayToHide = false;
			}
		}
	}
	
	/**
	 * Called when the box is selected
	 * Handles setting the state of each section
	 */
	public void select() {
		requestFocus();
		for (Section s : sections){
			s.select();
			if (getChildren().indexOf(s) == -1) {
				getChildren().add(s);
			}
		}
	}
	
	public Section[] getSections() {
		return sections;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int n) {
		id = n;
	}
	
	/**
	 * Translate this box to a representative string for saving
	 * 
	 * @param count - unique value for this box's id
	 * @return this box represented as a string
	 */
	public String serialize(int count) {
		id = count;
		String data="";
		data += getLayoutX() + "\n" + getLayoutY() + "\n";
		for (Section s : sections) {
			for (Node l : s.getChildren()) {
				data += ((TextLine) l).getText() + "\n";
			}
			data += "__section\n";
		}
		data += id + "\n";
		return data + "\n";
	}
}
