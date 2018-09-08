package waes.task.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import waes.task.exception.PreconditionException;
import waes.task.model.Text;
import waes.task.service.DiffedService;
import waes.task.util.Base64JsonUtil;
import waes.task.vo.DiffResult;
import waes.task.vo.ErrorInfo;

@RestController
@EnableAutoConfiguration
public class Diffed {

	@Autowired
	private DiffedService diffedService;
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@RequestMapping(value="/v1/diff/{id}/left",method=RequestMethod.PUT)
	public Text saveLeft(@PathVariable("id") Long id,@RequestBody String textJson) throws Exception{
		
		Text text = Base64JsonUtil.decodeToBean(textJson, Text.class);
		text.setId(id);
		
		return diffedService.saveLeft(text);
    }
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@RequestMapping(value="/v1/diff/{id}/right",method=RequestMethod.PUT)
	public Text saveRight(@PathVariable("id") Long id,@RequestBody String textJson) throws Exception{
		
		Text text = Base64JsonUtil.decodeToBean(textJson, Text.class);
		text.setId(id);
		
		return diffedService.saveRight(text);
    }
	
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value="/v1/diff/{id}",method=RequestMethod.GET)
	public DiffResult getDiffed(@PathVariable("id") Long id) throws Exception{
		
		return diffedService.getDiffed(id);
    }
		
	@ExceptionHandler(value = PreconditionException.class)
	@ResponseStatus(value=HttpStatus.PRECONDITION_REQUIRED)
	@ResponseBody
	public ErrorInfo errorResponse(Exception e) throws Exception	{
 
		ErrorInfo error = new ErrorInfo();
		error.setCode(HttpStatus.PRECONDITION_REQUIRED.value());		
		error.setException(e.getClass().getName());
		error.setMessage(e.getMessage());
		
		return error;
	}
		
}
