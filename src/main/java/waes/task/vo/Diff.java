package waes.task.vo;

/**
 * Value Object representing Diff detail via offset and length
 *
 */
public class Diff {

	/**
	 * offset begins from 0,not 1
	 */
	private Integer offset;
	/**
	 * length begins from 1
	 */
	private Integer length;
	
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
}
