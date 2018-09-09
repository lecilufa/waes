package waes.task.vo;

import java.util.List;

import waes.task.enums.DiffStatus;

/**
 * Value Object representing Diff Result, usually in below format
 * 
 *
 */
public class DiffResult {

	/**
	 * text id
	 */
	private Long id;
	
	/**
	 * <b>EQUAL</b>, when left and right are equal	<br>
	 * <b>NOT_SAME_SIZE</b>, when left and right are not same size	 <br>
	 * <b>DIFF</b> when left and right are same size,but different <br>
	 */
	private DiffStatus status;
	
	/**
	 * detailed diffed info present in offset and length
	 */
	private List<Diff> diffs;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DiffStatus getStatus() {
		return status;
	}
	public void setStatus(DiffStatus status) {
		this.status = status;
	}
	public List<Diff> getDiffs() {
		return diffs;
	}
	public void setDiffs(List<Diff> diffs) {
		this.diffs = diffs;
	}

	
	
	
}
