package waes.task.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import waes.task.constant.DiffStatus;
import waes.task.dao.DiffedDao;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.vo.Diff;
import waes.task.vo.DiffResult;

@Service
public class DiffedServiceImpl implements DiffedService {

	@Autowired
	private DiffedDao diffedDao;
	
	@Override
	public Text saveLeft(Text text) {
		
		Text existed = diffedDao.getTextById(text.getId());
		text.setRightText(null);
		if(existed == null){			
			diffedDao.saveText(text);			
			return diffedDao.getTextById(text.getId());
		}else{
			diffedDao.updateText(text);
			return diffedDao.getTextById(existed.getId());
		}
	}

	@Override
	public Text saveRight(Text text) {

		Text existed = diffedDao.getTextById(text.getId());
		text.setLeftText(null);
		if(existed == null){			
			diffedDao.saveText(text);			
			return diffedDao.getTextById(text.getId());
		}else{
			diffedDao.updateText(text);
			return diffedDao.getTextById(existed.getId());
		}
	}
	
	@Override
	public Text getTextById(Long id){
		return diffedDao.getTextById(id);
	}

	@Override
	public DiffResult getDiffed(Long id) {
		
		Text text = this.getTextById(id);
		
		DiffResult result = new DiffResult();
		
		String left = text.getLeftText();
		String right = text.getRightText();
		
		if(left == null && right == null){
			
		}
		
		if(left == null && right != null){
			
		}
		
		if(left != null && right == null){
			
		}
		
		
		if(left.equals(right)){
			result.setStatus(DiffStatus.EQUAL);
			result.setDiffs(Collections.emptyList());
		}else if(left.length() != right.length()){
			result.setStatus(DiffStatus.NOT_SAME_SIZE);
			result.setDiffs(Collections.emptyList());
		}else{
			result.setStatus(DiffStatus.DIFF);
			List<Diff> diffs = this.compareDiff(left,right);
			result.setDiffs(diffs);
		}
		
		return result;
	}

	public List<Diff> compareDiff(String left, String right) {
		
		List<Diff> diffs = new ArrayList<>();
		
		boolean begin = true;
		int length = 0;
		Diff diff = null;
		for(int i=0; i<left.length(); i++){
			Character leftChar = left.charAt(i);
			Character rightChar = right.charAt(i);
			
			if(leftChar != rightChar){
				
				if(begin){
					diff = new Diff();
					diff.setOffset(i);
					diffs.add(diff);
					
					begin = false;
					length = 0;
					diff.setLength(length);
				}else{
					length++;
					diff.setLength(length);
				}
			}else{
				begin = true;
			}
			
			
		}
		
		return diffs;
	}
	
	public static void main(String[] args) {
		String left =  "0123456789123456";
		String right = "012***67**12345*";
		
		List<Diff> diffs = new DiffedServiceImpl().compareDiff(left, right);
		
		System.out.println(diffs);
	}

}
