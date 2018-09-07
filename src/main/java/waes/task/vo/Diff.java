package waes.task.vo;

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
	
	
	@Override
	public String toString() {
		return "Diff [offset=" + offset + ", length=" + length + "]";
	}
	
	
	
}
