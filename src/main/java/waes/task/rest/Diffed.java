package waes.task.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.util.Base64JsonUtil;
import waes.task.vo.DiffResult;

@RestController
@EnableAutoConfiguration
public class Diffed {

	@Autowired
	private DiffedService diffedService;
	
	@RequestMapping(value="/v1/hi",method=RequestMethod.GET)
	Text hi(@RequestParam(value="text",required=false) Text text) throws Exception{
		
        return text;
    }
	
	@RequestMapping(value="/v1/diff/{id}/left",method=RequestMethod.PUT)
	Text saveLeft(@PathVariable("id") Long id,@RequestBody String textJson) throws Exception{
		
		Text text = Base64JsonUtil.toBean(textJson, Text.class);
		text.setId(id);
		
		return diffedService.saveLeft(text);
    }
	
	@RequestMapping(value="/v1/diff/{id}/right",method=RequestMethod.PUT)
	Text saveRight(@PathVariable("id") Long id,@RequestBody String textJson) throws Exception{
		
		Text text = Base64JsonUtil.toBean(textJson, Text.class);
		text.setId(id);
		
		return diffedService.saveRight(text);
    }
	
	@RequestMapping(value="/v1/diff/{id}",method=RequestMethod.GET)
	DiffResult getDiffed(@PathVariable("id") Long id) throws Exception{
		
		return diffedService.getDiffed(id);
    }
	
	
	
	
	@RequestMapping(value="/v1/encode",method=RequestMethod.PUT)
	String encode64(@RequestBody String textJson) throws Exception{
		
		String text = Base64JsonUtil.encode(textJson);
		
		return text;
    }
}
