package waes.task.vo;

import java.util.List;

import waes.task.enums.DiffStatus;

public class DiffResult {

	private DiffStatus status;
	private List<Diff> diffs;
	
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
