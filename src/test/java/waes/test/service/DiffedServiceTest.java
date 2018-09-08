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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { StartApp.class })
@WebAppConfiguration
public class DiffedServiceTest {

	@Autowired
	private DiffedService diffedService;

	@Test
	public void testDiffed() {
		diffedService.getDiffed(1L);
	}

	@Test
	public void saveLeft() {
		
		Text text = new Text();
		text.setLeftText("0123456789123456");
		
		Long newTextId = diffedService.saveLeft(text).getId();
		Text createdText = diffedService.getTextById(newTextId);
		assertEquals("0123456789123456", createdText.getLeftText());		
	}
	
	@Test
	public void saveRight() {

	}

}
