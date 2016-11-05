import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

/**
 * LineData Class
 * Stores critical information about the line objects that is put into the model
 * This is used for saving and opening the diagrams
 */
public class LineData implements Serializable{
	
	Integer startboxid;
	Integer endboxid;
	ArrayList<String> linetextdata;
	Integer id;

	/**
	 * LineData Constructor
	 * Takes in a new line object and stores it in the model
	 * @param startboxidin
	 * @param endboxidin
	 * @param linetextdata
	 * @param model
	 * @param id
	 */
	public LineData(int startboxidin, int endboxidin,ArrayList<String> linetextdata, Model model, Integer id) {
		this.startboxid = startboxidin;
		this.endboxid = endboxidin;
		this.linetextdata = linetextdata;
		this.id = id;
		model.getLineMap().put(id, this);
	}
	
	/**
	 * Takes in a specific linedata object and resets it's data in the model
	 * @param startboxidin
	 * @param endboxidin
	 * @param linetextdata
	 * @param model
	 * @param id
	 */
	public void ResetLineData(int startboxidin,int endboxidin,ArrayList<String> linetextdata, Model model, Integer id) {
		this.id = id;
		this.startboxid = startboxidin;
		this.endboxid = endboxidin;
		this.linetextdata = linetextdata;
		model.getLineMap().put(id, this);
	}

}