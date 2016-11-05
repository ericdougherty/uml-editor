import java.io.Serializable;
import java.util.ArrayList;

/**
 * RectangleData class
 * Class is similiar to the LindeData class, it posesses the critical information of
 * the box objects
 */
@SuppressWarnings("serial")
public class RectangleData implements Serializable{

	int width;
	int height;
	int xposition;
	int yposition;
	ArrayList<String> boxtextdata = new ArrayList<String>();
	Integer id;
	
	/**
	 * when a new RectangleData object is created it is passed off to be stored in
	 * the model
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @param model
	 * @param id
	 */
	public RectangleData(int i, int j, int k, int l, Model model, Integer id) {
		this.width = i;
		this.height = j;
		this.xposition = k;
		this.yposition = l;
		this.id = id;
		model.getBoxMap().put(id, this);
	}
	
	/**
	 * Whenever a box is modifed it's critical information is changed in the model
	 * @param width
	 * @param height
	 * @param xposition
	 * @param yposition
	 * @param boxtextdata
	 * @param model
	 * @param id
	 */
	public void ResetRectangleData(int width, int height, int xposition, int
			yposition, ArrayList<String> boxtextdata, Model model, Integer id) {
		this.width = width;
		this.height = height;
		this.xposition = xposition;
		this.yposition = yposition;
		this.boxtextdata = boxtextdata;
		this.id = id;
		model.getBoxMap().put(id, this);
	}
	
	/**
	 * a simple getter for the id of the box
	 * @return
	 */
	public int getId() {
		return id;
	}

}