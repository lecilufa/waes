package waes.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waes.task.StartApp;
import waes.task.enums.DiffStatus;
import waes.task.exception.PreconditionException;
import waes.task.model.Text;
import waes.task.rest.Diffed;
import waes.task.service.DiffedService;
import waes.task.util.Base64JsonUtil;
import waes.task.vo.Diff;
import waes.task.vo.DiffResult;
import waes.task.vo.ErrorInfo;

/**
 * test rest layer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApp.class })
@WebAppConfiguration
public class DiffedTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private Diffed diffed;
	@Autowired
	private DiffedService diffedService;

	/**
	 * test saving left text
	 */
	@Test
	public void saveLeft() throws Exception {
		
		Long id = 11L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789123456";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		diffed.saveLeft(id, encodeString);
		
		Text createdText = diffedService.getTextById(id);
		
		assertEquals(leftText, createdText.getLeftText());
		assertEquals(id, createdText.getId());
		
		
		// text update
		leftText = "012345678912**56";
		createdText.setLeftText(leftText);
		
		encodeString = Base64JsonUtil.encodeToString(createdText);		
		diffed.saveLeft(id, encodeString);
		
		createdText = diffedService.getTextById(id);
		
		assertEquals(leftText, createdText.getLeftText());
		assertEquals(id, createdText.getId());
		
	}
	
	/**
	 * test saving right text
	 */
	@Test
	public void saveRight() throws Exception{
		
		Long id = 12L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789123456";
		text.setRightText(rightText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		diffed.saveRight(id, encodeString);
		
		Text createdText = diffedService.getTextById(id);
		
		assertEquals(rightText, createdText.getRightText());	
		assertEquals(id, createdText.getId());
		
		// text update
		rightText = "0123ww6789123456";
		createdText.setRightText(rightText);
		
		encodeString = Base64JsonUtil.encodeToString(createdText);		
		diffed.saveRight(id, encodeString);
		
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
		
		Long id = 13L;
		
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
		
		DiffResult result = diffed.getDiffed(id);
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
	public void equal() throws Exception {
		Long id = 13L;
		
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
		
		DiffResult result = diffed.getDiffed(id);
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
	public void notSameSize() throws Exception {
		
		Long id = 14L;
		
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
		
		DiffResult result = diffed.getDiffed(id);
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
	public void emptyText() throws Exception {
		
		Long id = 100L;
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("No text with given id");
		diffed.getDiffed(id);
	}
	
	/**
	 * test get diffed info when left text not existed
	 * @throws Exception
	 */
	@Test
	public void leftTextEmpty() throws Exception {
		Long id = 15L;
	
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("Left text should not be null");
		diffed.getDiffed(id);
	}
	
	/**
	 * test get diffed info when right text not existed
	 * @throws Exception
	 */
	@Test
	public void rightTextEmpty() throws Exception {
		
		Long id = 16L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123**6789abcdefg";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("Right text should not be null");
		diffed.getDiffed(id);
	}
	
	/**
	 * text if error info is well presented
	 * @throws Exception
	 */
	@Test
	public void errorInfo() throws Exception {
		
		String message = "No text with given id";
		
		Exception e = new PreconditionException(message);
		ErrorInfo errorInfo = diffed.errorResponse(e);
		
		assertEquals(HttpStatus.PRECONDITION_REQUIRED.value(), errorInfo.getCode());
		assertEquals(e.getClass().getName(),errorInfo.getException());
		assertEquals(message,errorInfo.getMessage());
	}
	
}
