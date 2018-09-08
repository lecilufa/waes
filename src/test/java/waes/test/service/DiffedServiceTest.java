package waes.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waes.task.StartApp;
import waes.task.exception.PreconditionException;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.vo.DiffResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApp.class })
@WebAppConfiguration
public class DiffedServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private DiffedService diffedService;

	@Test
	public void saveLeft() {
		
		Long id = 1L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789123456";
		text.setLeftText(leftText);
		
		// text create
		diffedService.saveLeft(text);
		Text createdText = diffedService.getTextById(id);
		assertEquals(leftText, createdText.getLeftText());
		
		// text update
		leftText = "012345678912**56";
		createdText.setLeftText(leftText);
		diffedService.saveLeft(createdText);
		createdText = diffedService.getTextById(id);
		assertEquals(leftText, createdText.getLeftText());
		
	}
	
	@Test
	public void saveRight() {
		
		Long id = 2L;
		
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789123456";
		text.setRightText(rightText);
		
		// text create
		diffedService.saveRight(text);
		Text createdText = diffedService.getTextById(id);
		assertEquals(rightText, createdText.getRightText());	
		
		// text update
		rightText = "0123ww6789123456";
		createdText.setRightText(rightText);
		diffedService.saveRight(createdText);
		createdText = diffedService.getTextById(id);
		assertEquals(rightText, createdText.getRightText());
	}

	@Test
	public void diffed() {
		
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
	}
	
	@Test
	public void equal() {
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
	}
	
	@Test
	public void notSameSize() {
		
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
	}
	
	@Test
	public void emptyText() {
		
		Long id = 100L;
		
		thrown.expect(PreconditionException.class);
		thrown.expectMessage("No text with given id is to be Diffed.");
		diffedService.getDiffed(id);
	}
	
	@Test
	public void leftTextEmpty() {
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
	
	@Test
	public void rightTextEmpty() {
		
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
