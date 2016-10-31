import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class Relation extends Line {

<<<<<<< HEAD
	Box startBox;
	Box endBox;
	Controller controller;
	Input text;
=======
	private Box startBox = null;
	private Box endBox = null;
	private Controller controller;
	private Relation relation;
	private Input text;
	
	//relation types
	final int GENERALIZATION = 0;
	final int AGGREGATION = 1;
	//...
	private ImageView arrowHead;
>>>>>>> refs/remotes/origin/master

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
				controller.selectRelation(relation);
				// consume keeps event from interacting with elements below
				event.consume();
			}
		});
		
		text = new Input();
		
		arrowHead = new ImageView();
	}

<<<<<<< HEAD
	public void setEndPoint(Box b) {
		endBox = b;
=======
	public void setEndPoint(Box endBox) {
		this.endBox = endBox;
>>>>>>> refs/remotes/origin/master
		// not necessarily a grid position
		endXProperty().bind(endBox.layoutXProperty().add(endBox.widthProperty().divide(2)));
		endYProperty().bind(endBox.layoutYProperty().add(endBox.heightProperty().divide(2)));
		
<<<<<<< HEAD
		startBox.addRelation(this);
		endBox.addRelation(this);
		
		controller.workspace.getChildren().add(text);
		text.layoutXProperty().bind(startXProperty().add(endXProperty().subtract(text.widthProperty())).divide(2));
		text.layoutYProperty().bind(startYProperty().add(endYProperty().subtract(text.heightProperty().multiply(2))).divide(2));
=======
		addText();
		
		setRelationType(GENERALIZATION);
		controller.workspace.getChildren().add(arrowHead);
		controller.addRelation(this);
		
		arrowHead.layoutXProperty().bind(endXProperty().subtract(arrowHead.getImage().getWidth() / 2));
		arrowHead.layoutYProperty().bind(endYProperty().subtract(arrowHead.getImage().getHeight() / 2));
		
		updateArrowRotation();
>>>>>>> refs/remotes/origin/master
	}

	public Box getStartingBox() {
		return startBox;
	}
	
	public void addText() {
		controller.workspace.getChildren().add(text);
		text.layoutXProperty().bind(startXProperty().add(endXProperty().subtract(text.widthProperty())).divide(2));
		text.layoutYProperty().bind(startYProperty().add(endYProperty().subtract(text.heightProperty().multiply(2))).divide(2));
	}
	
	public Input getText() {
		return text;
	}
	
	public ImageView getArrowHead() {
		return arrowHead;
	}
	
	public void update() {
		updateArrowRotation();
	}
	
	public void updateArrowRotation() {
		double dx = endBox.getLayoutX() - startBox.getLayoutX();
		double dy = endBox.getLayoutY() - startBox.getLayoutY();
		double angle = Math.toDegrees(Math.atan(dy / dx));
		
		//adjusting degrees to range from [-180, 180], instead of [-90, 90]
		if (dx < 0) {
			if (dy < 0) {
				angle -= 180;
			} else {
				angle += 180;
			}
		}
		
		arrowHead.setRotate(angle);
	}
	
	public void setRelationType(int relationType) {
		if (relationType == GENERALIZATION) {
			arrowHead.setImage(new Image("/generalization.png", false));
		}
		//...
	}
	
	public void remove() {
		controller.removeRelation(this);
		controller.workspace.getChildren().remove(this);
		controller.workspace.getChildren().remove(text);
		controller.workspace.getChildren().remove(arrowHead);
	}
}
