import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;

/**
 * ContextMenu Class
 * Toolbar with buttons that change according to the currently selected object
 * -Currently has three states - Box selected, Relation selected, nothing selected
 */
public class ContextMenu extends VBox {

	Controller controller;
	
	//image views to act as "buttons"
	ImageView delete = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/delete.png"), 60, 60, true, true));
	ImageView addBox = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/addBox.png"), 60, 60, true, true));
	ImageView addRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/addRelation.png"), 60, 60, true, true));
    ImageView aggregation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/aggregation.png"), 60, 60, true, true));
    ImageView composition = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/composition.png"), 60, 60, true, true));
    ImageView association = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/association.png"), 60, 60, true, true));
    ImageView generalization = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/generalization.png"), 60, 60, true, true));
    ImageView solidLine = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/solidLine.png"), 60, 60, true, true));
    ImageView dottedLine = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/dottedLine.png"), 60, 60, true, true));
	ImageView singleRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/singleRelation.png"), 60, 60, true, true));
	ImageView doubleRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/doubleRelation.png"), 60, 60, true, true));
	ImageView flipRelation = new ImageView(new Image(getClass().getResourceAsStream("/ui elements/flipRelation.png"), 60, 60, true, true));
    
    Separator sep1 = new Separator();
    Separator sep2 = new Separator();
    Separator sep3 = new Separator();

    /**
     * Sets actions for all "buttons"
     * @param c - reference to controller
     */
	public ContextMenu(Controller c) {
		controller = c;
		
		//space between buttons
		setSpacing(10);
		setPadding(new Insets(20, 10, 10, 10));
		//preferred width - need to unify button widths and this won't be an issue
		setPrefWidth(80);
		
		//tooltips for buttons that may not be clear
		Tooltip.install(addBox, new Tooltip("Add Class Box"));
		Tooltip.install(addRelation, new Tooltip("Add Relation"));
		Tooltip.install(delete, new Tooltip("Delete"));
		Tooltip.install(aggregation, new Tooltip("Aggregation"));
		Tooltip.install(composition, new Tooltip("Composition"));
		Tooltip.install(association, new Tooltip("Association"));
		Tooltip.install(generalization, new Tooltip("Generalization"));
		Tooltip.install(solidLine, new Tooltip("Solid Line"));
		Tooltip.install(dottedLine, new Tooltip("Dotted Line"));
		Tooltip.install(singleRelation, new Tooltip("Single Ended"));
		Tooltip.install(doubleRelation, new Tooltip("Double Ended"));
		Tooltip.install(flipRelation, new Tooltip("Flip Relation"));
		
		getChildren().add(addBox);
		getStyleClass().add("vbox");
		//only available when a box or the workspace is selected
		addBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.deselectBox();
				new Box(controller);
				if (controller.isAddingRelation()) {
					controller.cancelCurrentRelation();
				}
			}
		});
		
		//only available when a box or relation is selected
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				controller.deleteSelected();
				if (controller.isAddingRelation()) {
					controller.cancelCurrentRelation();
				}
			}
		});

		//only available when a box is selected
		addRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (controller.isAddingRelation()) {
					controller.cancelCurrentRelation();
				} else {
					controller.startNewRelation();
				}
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
				if (!controller.getSelectedRelation().isSingleEnded()){
					controller.setCurrentRelationSingleEnded();
					setRelationEndingTypeShadow(true);
					flipRelationButton(true);
				}
			}
		});

		//only available when a relation is selected
		doubleRelation.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (controller.getSelectedRelation().isSingleEnded()){
					controller.setCurrentRelationDoubleEnded();
					setRelationEndingTypeShadow(false);
					flipRelationButton(false);
				}
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
	
	public void flipRelationButton(boolean show) {
		if (show) {
			getChildren().add(10, flipRelation);;
		}
		else {
			getChildren().remove(flipRelation);
		}
	}

	/**
	 * Displays all edit relation button
	 */
	public void showEditRelationButtons() {
		getChildren().add(aggregation);
		getChildren().add(composition);
		getChildren().add(association);
		getChildren().add(generalization);
		getChildren().add(sep1);
		getChildren().add(solidLine);
		getChildren().add(dottedLine);
		getChildren().add(sep2);
		getChildren().add(singleRelation);
		getChildren().add(doubleRelation);
		if (controller.getSelectedRelation().isSingleEnded()){
			getChildren().add(flipRelation);
		}
		getChildren().add(sep3);
		
	}
    
	/**
	 * Hides all edit relation button
	 */
	public void hideEditRelationButtons() {
		getChildren().remove(aggregation);
		getChildren().remove(composition);
		getChildren().remove(association);
		getChildren().remove(generalization);
		getChildren().remove(sep1);
		getChildren().remove(solidLine);
		getChildren().remove(dottedLine);
		getChildren().remove(sep2);
		getChildren().remove(singleRelation);
		getChildren().remove(doubleRelation);
		getChildren().remove(flipRelation);
		getChildren().remove(sep3);
	}
    
	/**
	 * highlight the button that matches the selected relations arrow head type
	 * @param relationType - selected relations arrow head type
	 */
    public void setArrowHeadTypeShadow(int relationType) {
    	//remove any highlight effects
    	aggregation.setEffect(null);
    	composition.setEffect(null);
    	association.setEffect(null);
    	generalization.setEffect(null);

    	//set applicable highlighting
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
    
    /**
     * highlight the button that matches the selected relations line type
     * @param dotted - true: (line is dotted), false: (line is solid) 
     */
    public void setLineTypeShadow(boolean dotted) {
    	//remove any highlight effects
    	solidLine.setEffect(null);
    	dottedLine.setEffect(null);

    	//set applicable highlighting
    	if (dotted) {
    		dottedLine.setEffect(new DropShadow(25, Color.WHITE));
    	} else {
    		solidLine.setEffect(new DropShadow(25, Color.WHITE));
    	}
    }
    
    /**
     * highlight the button that matches the selected relations number of arrow heads
     * @param singleEnded - true: (singleEnded), false: (double ended)
     */
    public void setRelationEndingTypeShadow(boolean singleEnded) {
    	//remove any highlight effects
    	singleRelation.setEffect(null);
    	doubleRelation.setEffect(null);
    	
    	//set applicable highlighting
    	if (singleEnded) {
    		singleRelation.setEffect(new DropShadow(25, Color.WHITE));
    	} else {
    		doubleRelation.setEffect(new DropShadow(25, Color.WHITE));
    	}
    }
    
	/**
	 * applies a highlight or removes a highlight from the addRelation button
	 * @param b - true: (apply highlight), false: (remove highlight)
	 */
	public void setAddRelationShadow(boolean b) {
		if (b) {
			addRelation.setEffect(new DropShadow(25, Color.WHITE));
		} else {
			addRelation.setEffect(null);
		}
	}
}