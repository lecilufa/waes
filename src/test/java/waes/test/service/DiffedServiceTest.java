package waes.test.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waes.task.StartApp;
import waes.task.enums.DiffStatus;
import waes.task.exception.PreconditionException;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.vo.Diff;
import waes.task.vo.DiffResult;

/**
 * test service layer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApp.class })
@WebAppConfiguration
public class DiffedServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private DiffedService diffedService;

	/**
	 * test saving left text
	 */
	@Test
	public void saveLeft() {
		
		Long id = 1L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789123456";
		text.setLeftText(leftText);
		diffedService.saveLeft(text);
		Text createdText = diffedService.getTextById(id);
		
		assertEquals(leftText, createdText.getLeftText());
		assertEquals(id, createdText.getId());
		
		// text update
		leftText = "012345678912**56";
		createdText.setLeftText(leftText);
		diffedService.saveLeft(createdText);
		createdText = diffedService.getTextById(id);
		
		assertEquals(leftText, createdText.getLeftText());
		assertEquals(id, createdText.getId());
		
	}
	
	/**
	 * test saving right text
	 */
	@Test
	public void saveRight() {
		
		Long id = 2L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789123456";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		Text createdText = diffedService.getTextById(id);
		
		assertEquals(rightText, createdText.getRightText());	
		assertEquals(id, createdText.getId());
		
		// text update
		rightText = "0123ww6789123456";
		createdText.setRightText(rightText);
		diffedService.saveRight(createdText);
		createdText = diffedService.getTextById(id);
		
		assertEquals(rightText, createdText.getRightText());
		assertEquals(id, createdText.getId());
	}

	/**
	 * test get diffed info when left and right text are same size but contents are different
	 * @throws Exception
	 */
	@Test
	public void diffed() throws Exception {
		
		Long id = 3L;
		
		//test 'DIFF'
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789abcdefg";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		DiffResult result = diffedService.getDiffed(id);
		DiffStatus status = result.getStatus();
		List<Diff> diffs = result.getDiffs();
		
		assertEquals(id, result.getId());
		assertEquals(DiffStatus.DIFF,status);
		assertTrue(diffs.size() > 0);
		assertEquals(new Integer(4),diffs.get(0).getOffset());
		assertEquals(new Integer(2),diffs.get(0).getLength());
		
	}
	
	/**
	 * test get diffed info when left and right text are equal
	 * @throws Exception
	 */
	@Test
	public void equal() throws Exception{
		Long id = 3L;
		
		//test 'DIFF'
		Text text = new Text();
		text.setId(id);
		String leftText = "123456";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		text = new Text();
		text.setId(id);
		String rightText = "123456";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		DiffResult result = diffedService.getDiffed(id);
		DiffStatus status = result.getStatus();
		List<Diff> diffs = result.getDiffs();
		
		assertEquals(id, result.getId());
		assertEquals(DiffStatus.EQUAL,status);
		assertTrue(diffs.size() == 0);
	}
	
	/**
	 * test get diffed info when left and right text are not same size
	 * @throws Exception
	 */
	@Test
	public void notSameSize() throws Exception{
		
		Long id = 4L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789abcdefg";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg123";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		DiffResult result = diffedService.getDiffed(id);
		DiffStatus status = result.getStatus();
		List<Diff> diffs = result.getDiffs();
		
		assertEquals(id, result.getId());
		assertEquals(DiffStatus.NOT_SAME_SIZE,status);
		assertTrue(diffs.size() == 0);
	}
	
	/**
	 * test get diffed info when text not existed
	 * @throws Exception
	 */
	@Test
	public void emptyText() throws Exception{
		
		Long id = 100L;
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("No text with given id");
		diffedService.getDiffed(id);
	}
	
	/**
	 * test get diffed info when left text not existed
	 * @throws Exception
	 */
	@Test
	public void leftTextEmpty() throws Exception{
		Long id = 5L;
	
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("Left text should not be null");
		diffedService.getDiffed(id);
	}
	
	/**
	 * test get diffed info when right text not existed
	 * @throws Exception
	 */
	@Test
	public void rightTextEmpty() throws Exception{
		
		Long id = 6L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123**6789abcdefg";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("Right text should not be null");
		diffedService.getDiffed(id);
	}
	
}
