package waes.task.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import waes.task.dao.DiffedDao;
import waes.task.enums.DiffStatus;
import waes.task.exception.PreconditionException;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.vo.Diff;
import waes.task.vo.DiffResult;

@Service
public class DiffedServiceImpl implements DiffedService {

	@Autowired
	private DiffedDao diffedDao;
	
	@Transactional
	@Override
	public Text saveLeft(Text text) {
		
		Text existed = diffedDao.getTextById(text.getId());
		
		if(existed == null){			
			text.setRightText(null);
			diffedDao.saveText(text);			
			return diffedDao.getTextById(text.getId());
		}else{
			existed.setLeftText(text.getLeftText());
			diffedDao.updateText(existed);
			return diffedDao.getTextById(existed.getId());
		}
	}

	@Transactional
	@Override
	public Text saveRight(Text text) {

		Text existed = diffedDao.getTextById(text.getId());
		
		if(existed == null){
			text.setLeftText(null);
			diffedDao.saveText(text);			
			return diffedDao.getTextById(text.getId());
		}else{
			existed.setRightText(text.getRightText());
			diffedDao.updateText(existed);
			return diffedDao.getTextById(existed.getId());
		}
	}
	
	@Override
	public Text getTextById(Long id){
		return diffedDao.getTextById(id);
	}

	@Transactional
	@Override
	public DiffResult getDiffed(Long id) {
		
		Text text = this.getTextById(id);
		if(text == null){
			throw new PreconditionException("No text with given id");
		}
		
		String left = text.getLeftText();
		String right = text.getRightText();
		
		if(left == null){
			throw new PreconditionException("Left text should not be null");
		}else if(right == null){
			throw new PreconditionException("Right text should not be null");
		}
		
		DiffResult result = new DiffResult();
		result.setId(id);
		
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

	private List<Diff> compareDiff(String left, String right) {
		
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
					length = 1;
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
	
}
