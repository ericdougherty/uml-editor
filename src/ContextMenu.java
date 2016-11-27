import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
	
	ImageView delete = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/delete.png"), 60, 60, true, true));
	ImageView addBox = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/addBox.png"), 60, 60, true, true));
	ImageView addRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/addRelation.png"), 60, 60, true, true));
	ImageView flipRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/flipRelation.png"), 60, 60, true, true));
	ImageView singleRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/singleRelation.png"), 60, 60, true, true));
	ImageView doubleRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/doubleRelation.png"), 60, 60, true, true));
    
    ImageView aggregation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/aggregation.png"), 60, 60, true, true));
    ImageView composition = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/composition.png"), 60, 60, true, true));
    ImageView association = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/association.png"), 60, 60, true, true));
    ImageView generalization = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/generalization.png"), 60, 60, true, true));
    ImageView solidLine = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/solidLine.png"), 60, 60, true, true));
    ImageView dottedLine = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/dottedLine.png"), 60, 60, true, true));

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
		
		Tooltip.install(addBox, new Tooltip("Add Class Box"));
		Tooltip.install(addRelation, new Tooltip("Add Relation"));
		Tooltip.install(delete, new Tooltip("Delete"));
		Tooltip.install(flipRelation, new Tooltip("Flip Relation"));
		Tooltip.install(singleRelation, new Tooltip("Make Relation Single Ended"));
		Tooltip.install(doubleRelation, new Tooltip("Make Relation Double Ended"));
		
		getChildren().add(addBox);
		getStyleClass().add("vbox");

		addBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.deselectBox();
				new Box(controller);
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

		//only available when a relation is selected and single ended
		flipRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.flipCurrentRelation();
			}
		});

		//only available when a relation is selected
		singleRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.setCurrentRelationSingleEnded();
				setRelationEndingTypeShadow(true);
			}
		});

		//only available when a relation is selected
		doubleRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.setCurrentRelationDoubleEnded();
				setRelationEndingTypeShadow(false);
			}
		});

		//only available when a relation is selected
		aggregation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0){
			    controller.setRelationType(Relation.AGGREGATION);
			    setArrowHeadTypeShadow(Relation.AGGREGATION);
			}
		});

		//only available when a relation is selected
		composition.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0){
			    controller.setRelationType(Relation.COMPOSITION);
			    setArrowHeadTypeShadow(Relation.COMPOSITION);
			}
		});

		//only available when a relation is selected
		association.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0){
			    controller.setRelationType(Relation.ASSOCIATION);
			    setArrowHeadTypeShadow(Relation.ASSOCIATION);
			}
		});

		//only available when a relation is selected
		generalization.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent agr0){
			    controller.setRelationType(Relation.GENERALIZATION);
			    setArrowHeadTypeShadow(Relation.GENERALIZATION);
			}
		});

		//only available when a relation is selected
        solidLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.setCurrentRelationSolid();
				setLineTypeShadow(false);
			}
		});

		//only available when a relation is selected
        dottedLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.setCurrentRelationDotted();
				setLineTypeShadow(true);
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
	 * Displays the appropriate edit relation button
	 */
	public void showEditRelationButtons() {
		getChildren().add(aggregation);
		getChildren().add(composition);
		getChildren().add(association);
		getChildren().add(generalization);
		getChildren().add(solidLine);
		getChildren().add(dottedLine);
		getChildren().add(singleRelation);
		getChildren().add(doubleRelation);
		getChildren().add(flipRelation);
	}
    
	/**
	 * Hides the appropriate edit relation button
	 */
	public void hideEditRelationButtons() {
		getChildren().remove(aggregation);
		getChildren().remove(composition);
		getChildren().remove(association);
		getChildren().remove(generalization);
		getChildren().remove(solidLine);
		getChildren().remove(dottedLine);
		getChildren().remove(singleRelation);
		getChildren().remove(doubleRelation);
		getChildren().remove(flipRelation);
	}
    
    public void setArrowHeadTypeShadow(int relationType) {
    	aggregation.setEffect(null);
    	composition.setEffect(null);
    	association.setEffect(null);
    	generalization.setEffect(null);
    	if (relationType == Relation.AGGREGATION) {
    		aggregation.setEffect(new DropShadow(25, Color.WHITE));
    	} else if (relationType == Relation.COMPOSITION) {
    		composition.setEffect(new DropShadow(25, Color.WHITE));
    	} else if (relationType == Relation.ASSOCIATION) {
    		association.setEffect(new DropShadow(25, Color.WHITE));
    	} else if (relationType == Relation.GENERALIZATION) {
    		generalization.setEffect(new DropShadow(25, Color.WHITE));
    	}
    }
    
    public void setLineTypeShadow(boolean dotted) {
    	solidLine.setEffect(null);
    	dottedLine.setEffect(null);
    	if (dotted) {
    		dottedLine.setEffect(new DropShadow(25, Color.WHITE));
    	} else {
    		solidLine.setEffect(new DropShadow(25, Color.WHITE));
    	}
    }
    
    public void setRelationEndingTypeShadow(boolean singleEnded) {
    	singleRelation.setEffect(null);
    	doubleRelation.setEffect(null);
    	if (singleEnded) {
    		singleRelation.setEffect(new DropShadow(25, Color.WHITE));
    	} else {
    		doubleRelation.setEffect(new DropShadow(25, Color.WHITE));
    	}
    }
    
	/**
	 * applies a shadow or removes a shadow from the addRelation button
	 * @param b - boolean for whether a shadow should be applied or removed
	 */
	public void setAddRelationShadow(boolean b) {
		if (b) {
			addRelation.setEffect(new DropShadow(25, Color.WHITE));
		} else {
			addRelation.setEffect(null);
		}
	}

}