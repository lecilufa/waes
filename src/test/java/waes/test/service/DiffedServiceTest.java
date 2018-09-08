package waes.test.service;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import waes.task.StartApp;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.vo.DiffResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApp.class })
@WebAppConfiguration
public class DiffedServiceTest {

	@Autowired
	private DiffedService diffedService;

	@Test
	public void saveLeft() {
		
		Text text = new Text();
		text.setId(1L);
		String leftText = "0123456789123456";
		text.setLeftText(leftText);
		
		Long newTextId = diffedService.saveLeft(text).getId();
		Text createdText = diffedService.getTextById(newTextId);
		assertEquals("0123456789123456", createdText.getLeftText());		
	}
	
	@Test
	public void saveRight() {
		Text text = new Text();
		text.setId(1L);
		String rightText = "0123**6789123456";
		text.setRightText(rightText);
		
		Long newTextId = diffedService.saveRight(text).getId();
		Text createdText = diffedService.getTextById(newTextId);
		assertEquals(rightText, createdText.getRightText());	
	}

	@Test
	public void diffed() {
		
		Long id = 2L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789abcdefg";
		text.setLeftText(leftText);
		
		diffedService.saveLeft(text);
		
		text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg1";
		text.setRightText(rightText);
		
		diffedService.saveRight(text);
		
		DiffResult result = diffedService.getDiffed(id);
		
		System.out.println(result);
	}
}
