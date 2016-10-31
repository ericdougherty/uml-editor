import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class Relation extends Line {

	Box startBox;
	Box endBox;
	Controller controller;
	Input text;

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
	}

	public void setEndPoint(Box b) {
		endBox = b;
		// not necessarily a grid position
		endXProperty().bind(endBox.layoutXProperty().add(endBox.widthProperty().divide(2)));
		endYProperty().bind(endBox.layoutYProperty().add(endBox.heightProperty().divide(2)));
		
		startBox.addRelation(this);
		endBox.addRelation(this);
		
		controller.workspace.getChildren().add(text);
		text.layoutXProperty().bind(startXProperty().add(endXProperty().subtract(text.widthProperty())).divide(2));
		text.layoutYProperty().bind(startYProperty().add(endYProperty().subtract(text.heightProperty().multiply(2))).divide(2));
	}

	public Box getStartingBox() {
		return startBox;
	}
}
