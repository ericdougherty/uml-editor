import java.util.HashSet;
import java.util.Set;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Controller {

	ContextMenu toolbar;
	FileMenu menu;
	WorkSpace workspace;
	BorderPane ui;
	Box selectedBox = null;
	private Relation currentRelation = null;
	private boolean addingRelation = false;
	private Relation selectedRelation;
	private Set<Relation> relations;

	public Controller() {
		toolbar = new ContextMenu(this);
		menu = new FileMenu(this);
		workspace = new WorkSpace(this);
		ui = new BorderPane();
		relations = new HashSet<Relation>();
		
		ui.setLeft(toolbar);
		ui.setTop(menu);
		ui.setCenter(workspace);
	}
	
	public void selectBox(Box box) {
		
		deselectRelation();
		
		if (selectedBox == null) {
			selectedBox = box;
			toolbar.showAddRelationButton();
			toolbar.showDeleteButton();
			selectedBox.getStyleClass().add("box-shadow");
			selectedBox.select();
		} 
		else if (box != selectedBox) {
			selectedBox.getStyleClass().remove("box-shadow");
			selectedBox = box;
			selectedBox.getStyleClass().add("box-shadow");
			selectedBox.select();
		}
	}

	public void deleteSelected() {
		if (selectedBox != null) {
			for (Relation r  : selectedBox.relations) {
				if (r.endBox == selectedBox){
					r.startBox.relations.remove(r);
				}
				else {
					r.endBox.relations.remove(r);
				}
				r.remove();
			}
			workspace.getChildren().remove(selectedBox);
			toolbar.hideDeleteButton();
			toolbar.hideAddRelationButton();
			//remove any relations attached to the box being removed
			for (Relation r : relations) {
				if (r.getEndingBox() == selectedBox || r.getStartingBox() == selectedBox) {
					r.remove();
				}
			}
			selectedBox = null;
		}
		if (selectedRelation != null) {
			selectedRelation.remove();
			toolbar.hideDeleteButton();
			toolbar.showAddBoxButton();
			selectedRelation = null;
		}
	}
	
	public void deselectBox() {

		if (selectedBox != null){
			toolbar.hideDeleteButton();
			toolbar.hideAddRelationButton();
			selectedBox.deselect();
			selectedBox.getStyleClass().remove("box-shadow");
			selectedBox = null;
			cancelCurrentRelation();
		}
	}
	
	public void deselectRelation() {
		
		if (selectedRelation != null){
			toolbar.hideDeleteButton();
			toolbar.showAddBoxButton();
			selectedRelation.setStroke(Color.GRAY);
			selectedRelation = null;
		}
		
	}
	
	public void showGrid() {
		workspace.getStyleClass().add("noGrid");
	}
	
	public void startNewRelation() {
		if (selectedBox != null) {
			addingRelation = true;
			currentRelation = new Relation(selectedBox, this);
		}
	}
	
	public void endCurrentRelation(Box b) {
		//only end relation if a box is selected
		//and the ending box and starting box are different
		if (b != null && !b.equals(currentRelation.getStartingBox())) {
			currentRelation.setEndPoint(b);
			workspace.getChildren().add(currentRelation);
			currentRelation.toBack();
			currentRelation = null;
			addingRelation = false;
		} else {
			//invalid ending box
			cancelCurrentRelation();
		}
	}
	
	public boolean isAddingRelation() {
		return addingRelation;
	}
	
	public void cancelCurrentRelation() {
		addingRelation = false;
		currentRelation = null;
	}

	public void selectRelation(Relation relation) {
		
		deselectBox();
		
		if (selectedRelation == null) {
			selectedRelation = relation;
			selectedRelation.setStroke(Color.WHITE);
			toolbar.hideAddBoxButton();
			toolbar.hideAddRelationButton();
			toolbar.showDeleteButton();
		} 
		else if (selectedRelation != relation) {
			selectedRelation.setStroke(null);
			selectedRelation = relation;
			selectedRelation.setStroke(Color.WHITE);
		}
	}
	
	public void addRelation(Relation r) {
		relations.add(r);
	}
	
	public void removeRelation(Relation r) {
		relations.remove(r);
	}
	
	public void updateRelations() {
		for (Relation r : relations) {
			r.update();
		}
	}
}
