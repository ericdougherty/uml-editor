import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;

public class Relation extends Line {

	private Box startBox = null;
	Controller controller;
	Relation relation;

	public Relation(Box startBox, Controller c) {
		this.controller = c;
		this.startBox = startBox;
		DoubleProperty startX = new SimpleDoubleProperty();
		DoubleProperty startY = new SimpleDoubleProperty();
		startXProperty().bind(startX);
		startYProperty().bind(startY);
		startX.bind(startBox.layoutXProperty().add(startBox.widthProperty().divide(2)));
		startY.bind(startBox.layoutYProperty().add(startBox.heightProperty().divide(2)));
		final Relation relation = this;
		
		getStyleClass().add("relation");
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.selectRelation(relation);
				//consume keeps event from interacting with elements below
				event.consume();
			}
		});
	}

	public void setEndPoint(Box endBox) {
		DoubleProperty endX = new SimpleDoubleProperty();
		DoubleProperty endY = new SimpleDoubleProperty();
		endXProperty().bind(endX);
		endYProperty().bind(endY);
		endX.bind(endBox.layoutXProperty().add(endBox.widthProperty().divide(2)));
		endY.bind(endBox.layoutYProperty().add(endBox.heightProperty().divide(2)));
	}

	public Box getStartingBox() {
		return startBox;
	}
}
