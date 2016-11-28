import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class Relation extends Line {
	/** Class Invariant:
    1. controller always refers to the same controller, and never is null after assignment
    2. arrowHead and endBox are never null after assignment
	*/

	private Box startBox = null;
	private Box endBox = null;
	private Controller controller;
	private TextLine text;
	private Input input = new Input(this);
    private int relationType = GENERALIZATION;
    private boolean dotted = false;
	
	//relation types
	static final int GENERALIZATION = 0;
	static final int AGGREGATION = 1;
    static final int ASSOCIATION = 2;
    static final int COMPOSITION = 3;
    
	private ImageView arrowHead;
	private ImageView secondArrowHead;
	
	/**
	 * Assigns startBox and controller, to the passed parameters
	 * Click event to select this relation
	 * This relation is initially selected
	 * startX and startY are bound to middle of startBox
	 * 
	 * @param startBox - box to bound startX and startY
	 * @param c - controller that will be notified of events on this relation
	 * @postcondition
	 * 		startBox, controller, text, and arrowHead are not null
	 */
	public Relation(Box startBox, Controller c) {
		this.controller = c;
		this.startBox = startBox;
		// not necessarily a grid position
		startXProperty().bind(startBox.layoutXProperty().add(startBox.widthProperty().divide(2)));
		startYProperty().bind(startBox.layoutYProperty().add(startBox.heightProperty().divide(2)));
		
		final Relation relation = this;

		getStyleClass().add("relation");

		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!controller.isAddingRelation()) {
					controller.selectRelation(relation);
					// consume keeps event from interacting with elements below
					event.consume();
				} else {
					controller.displayInvalidRelationMessage();
				}
			}
		});
		
		text = new TextLine("add text here", this);
		
		arrowHead = new ImageView();
	}


	/**
	 * Assigns endBox to passed parameter
	 * endX and endY are bound to middle of endBox
	 * Arrow head and optional text are added to relation
	 * This relation is added to the workspace and displayed
	 * 
	 * @param endBox - box to bind endX and endY
	 */
	public void setEndPoint(Box endBox) {
		this.endBox = endBox;
		// not necessarily a grid position
		endXProperty().bind(endBox.layoutXProperty().add(endBox.widthProperty().divide(2)));
		endYProperty().bind(endBox.layoutYProperty().add(endBox.heightProperty().divide(2)));
		
		setRelationType(GENERALIZATION);
		controller.workspace.getChildren().add(arrowHead);
		addText();
		update();
		
		Relation r = this;
		arrowHead.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.selectRelation(r);
				// consume keeps event from interacting with elements below
				event.consume();
			}
		});
	}
	
	public void setTempEndPoint(double x, double y) {
		setEndX(x);
		setEndY(y);
	}

	public Box getStartBox() {
		return startBox;
	}
	
	public Box getEndBox() {
		return endBox;
	}
	
	/**
	 * Binds text to midpoint of this relation
	 */
	public void addText() {
		text.layoutXProperty().bind(startXProperty().add(endXProperty()).divide(2));
		text.layoutYProperty().bind(startYProperty().add(endYProperty().subtract(5)).divide(2));
	}
	
	/**
	 * Text is added to workspace to be displayed
	 */
	public void showText() {
		if (!controller.workspace.getChildren().contains(text)) {
			controller.workspace.getChildren().add(text);
		}
	}
	
	/**
	 * Text is removed from workspace
	 */
	public void hideText() {
		if (text.getText().equals("add text here") || text.getText().trim().equals("")) {
			text.setText("add text here");
			controller.workspace.getChildren().remove(text);
		}
	}
	
	/**
	 * @return text on relation
	 */
	public TextLine getText() {
		return text;
	}
	
	public void setText(String s) {
		text.setText(s);
	}
	
	/**
	 * An input box is bound to the midpoint of the line
	 * @param s - string to be displayed in input box bound to this relation
	 */
	public void addInput(String s) {
		if (s.equals("add text here"))
			input.setText("");
		else
			input.setText(s);
		controller.workspace.getChildren().remove(text);
		controller.workspace.getChildren().add(input);
		
		input.layoutXProperty().bind(startXProperty().add(endXProperty()).divide(2));
		input.layoutYProperty().bind(startYProperty().add(endYProperty().subtract(input.heightProperty().multiply(2))).divide(2));

		input.requestFocus();
	}
	
	/**
	 * Takes text from input box and displays as plain text on relation
	 */
	public void processInput() {
		text.setText(input.getText());
		controller.workspace.getChildren().remove(input);
		showText();
		hideText();
	}
	
	public ImageView getArrowHead() {
		return arrowHead;
	}
	
	/**
	 * arrow heads are to be properly positioned and rotated
	 */
	public void update() {
		updateArrowPosition(arrowHead, startBox, endBox);
		updateArrowRotation(arrowHead, startBox, endBox);
		if (!isSingleEnded()) {
			updateArrowPosition(secondArrowHead, endBox, startBox);
			updateArrowRotation(secondArrowHead, endBox, startBox);
		}
		
	}
	
	/**
	 * Arrow head rotation is updated to match that of the line
	 */
	public void updateArrowRotation(ImageView arrowHead, Box startBox, Box endBox) {
		arrowHead.setRotate(getLineAngle(startBox, endBox));
	}
	
	/**
	 * Arrow head is positioned to be where this relation and the end box intersect
	 * startBox and endBox can be passed in reverse order to apply the arrow head to the startBox instance variable
	 * @param arrowHead - which arrowHead to position
	 * @param startBox
	 * @param endBox
	 */
	public void updateArrowPosition(ImageView arrowHead, Box startBox, Box endBox) {
		double angle = getLineAngle(startBox, endBox);
		double halfBoxWidth = endBox.getWidth() / 2;
		double halfBoxHeight = endBox.getHeight() / 2;
		
		//angle from x-axis where line would intersect corner of box
		double criticalAngle = Math.toDegrees(Math.atan(endBox.getHeight() / endBox.getWidth()));
		
		double xOffset = 0;
		double yOffset = 0;
		
		//for if line intersects top or bottom of box
		double partialBoxWidth = halfBoxHeight / Math.abs(Math.tan(Math.toRadians(angle)));
		//for if line intersects left or right side of box
		double partialBoxHeight = Math.abs(Math.tan(Math.toRadians(angle))) * halfBoxWidth;
		
		//calculate where line intersects outside of box based on axis and corners
		if (angle >= 0 && angle < criticalAngle) {
			xOffset = -halfBoxWidth;
			yOffset = -partialBoxHeight;
		} else if (angle >= criticalAngle && angle < 90) {
			xOffset = -partialBoxWidth;
			yOffset = -halfBoxHeight;
		} else if (angle >= 90 && angle < 180 - criticalAngle) {
			xOffset = partialBoxWidth;
			yOffset = -halfBoxHeight;
		} else if (angle >= 180 - criticalAngle && angle <= 180) {
			xOffset = halfBoxWidth;
			yOffset = -partialBoxHeight;
		} else if (angle >= -180 && angle < -180 + criticalAngle) {
			xOffset = halfBoxWidth;
			yOffset = partialBoxHeight;
		} else if (angle >= -180 + criticalAngle && angle < -90) {
			xOffset = partialBoxWidth;
			yOffset = halfBoxHeight;
		} else if (angle >= -90 && angle < -criticalAngle) {
			xOffset = -partialBoxWidth;
			yOffset = halfBoxHeight;
		} else if (angle >= -criticalAngle && angle < 0) {
			xOffset = -halfBoxWidth;
			yOffset = partialBoxHeight;
		}
		
		//sets middle of arrow head to edge of box using offsets
		arrowHead.setX(endBox.getLayoutX() + (endBox.getWidth() / 2) - (arrowHead.getImage().getWidth() / 2) + xOffset);
		arrowHead.setY(endBox.getLayoutY() + (endBox.getHeight() / 2) - (arrowHead.getImage().getHeight() /2) + yOffset);
	}
	
	/**
	 * Arrow heads are set based on type of relation desired
	 * secondArrowHead is updated if the relation is double ended
	 * @param relationType - determines type of arrow head
	 */
	public void setRelationType(int relationType) {
		this.relationType = relationType;
		if (relationType == GENERALIZATION) {
			arrowHead.setImage(new Image("/ui elements/gen.png", false));
		}
		if (relationType == ASSOCIATION) {
			arrowHead.setImage(new Image("/ui elements/assoc.png", false));
		}
		if (relationType == AGGREGATION) {
			arrowHead.setImage(new Image("/ui elements/agg.png", false));
		}
		if (relationType == COMPOSITION) {
			arrowHead.setImage(new Image("/ui elements/comp.png", false));
		}
		
		if (!isSingleEnded()) {
			secondArrowHead.setImage(arrowHead.getImage());
		}
		update();
	}
	
	public int getRelationType() {
		return relationType;
	}
	
	/**
	 * removes this relation, the text on this relation, and the arrow head from the workspace
	 */
	public void remove() {
		controller.workspace.getChildren().remove(this);
		controller.workspace.getChildren().remove(text);
		controller.workspace.getChildren().remove(arrowHead);
		controller.workspace.getChildren().remove(secondArrowHead);
	}
	
	/**
	 * Angle between line and x-axis through startBox in degrees
	 * Calculated as though startX and startY = 0
	 * Easiest to think about when moving the endBox in a circle around the startBox
	 * @return angle between line and x-axis through startBox in degrees
	 */
	private double getLineAngle(Box startBox, Box endBox) {
		//angle from startingBox to endingBox
		double dx = (endBox.getLayoutX() + (endBox.getWidth() / 2)) - (startBox.getLayoutX() + (startBox.getWidth() / 2));
		double dy = (endBox.getLayoutY() + (endBox.getHeight() / 2)) - (startBox.getLayoutY() + (startBox.getHeight() / 2));
		double angle = Math.toDegrees(Math.atan(dy / dx));
		
		//adjusting degrees to range from [-180, 180], instead of [-90, 90]
		if (dx < 0) {
			if (dy < 0) {
				angle -= 180;
			} else {
				angle += 180;
			}
		}
		return angle;
	}
	
	/**
	 * @return reference to controller
	 */
	public Controller getController() {
		return controller;
	}
	
	/**
	 * swap assignments of startBox and endBox
	 */
	public void flip() {
		Box temp = startBox;
		startBox = endBox;
		endBox = temp;
		update();
	}
	
	/**
	 * remove secondArrowHead
	 */
	public void setSingleEnded() {
		controller.workspace.getChildren().remove(secondArrowHead);
		secondArrowHead = null;
		update();
	}
	
	/**
	 * secondArrowHead is added and its type matches that of arrowHead
	 */
	public void setDoubleEnded() {
		if (secondArrowHead == null) {
			secondArrowHead = new ImageView(arrowHead.getImage());
			controller.workspace.getChildren().add(secondArrowHead);
			update();
			
			Relation r = this;
			secondArrowHead.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					controller.selectRelation(r);
					// consume keeps event from interacting with elements below
					event.consume();
				}
			});
		}
	}
	
	public boolean isSingleEnded() {
		return secondArrowHead == null;
	}
    
    public void setSolid() {
		getStyleClass().remove("relation-dotted");
		dotted = false;
    }
    
    public void setDotted() {
		getStyleClass().add("relation-dotted");
		dotted = true;
    }
    
	public boolean isDotted() {
		return dotted;
	}

    public String serialize() {
    	String data = "";
    	data += startBox.getID() + "\n";
    	data += endBox.getID() + "\n";
    	data += text.getText() + "\n";
    	data += relationType + "\n";
    	if (secondArrowHead != null) {
    		data += "second\n";
    	}
    	else {
    		data += "\n";
    	}
    	if (isDotted()) {
    		data += "dotted\n";
    	}
    	else {
    		data += "\n";
    	}
    	
    	return data + "\n";
    }
}
