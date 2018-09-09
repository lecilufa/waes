package waes.task.model;

/**
 * Text entity representing leftText and rightText send by rest clients.
 *
 */
public class Text {

	private Long id;
	private String leftText;
	private String rightText;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLeftText() {
		return leftText;
	}
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}
	public String getRightText() {
		return rightText;
	}
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}
	
	
}
