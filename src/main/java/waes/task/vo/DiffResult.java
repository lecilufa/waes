package waes.task.vo;

import java.util.List;

import waes.task.enums.DiffStatus;

public class DiffResult {

	private Long id;
	private DiffStatus status;
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
