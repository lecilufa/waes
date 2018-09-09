package waes.task.vo;

/**
 * Value Object representing Diff detail via offset and length
 *
 */
public class Diff {

	private Integer offset;
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
