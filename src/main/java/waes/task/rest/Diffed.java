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
	
	/**
	 * save or update left text, if not existed create one, if existed, update it
	 * 
	 * @param id	text id
	 * @param encodedText	json format left text encoded by Base64
	 * @return	text entity that stored in database
	 * @throws Exception
	 */
	@ResponseStatus(value=HttpStatus.CREATED)
	@RequestMapping(value="/v1/diff/{id}/left",method=RequestMethod.PUT)
	public Text saveLeft(@PathVariable("id") Long id,@RequestBody String encodedText) throws Exception{
		
		Text text = Base64JsonUtil.decodeToBean(encodedText, Text.class);
		text.setId(id);
		
		return diffedService.saveLeft(text);
    }
	
	/**
	 * save or update right text, if not existed create one, if existed, update it
	 * 
	 * @param id	text id
	 * @param encodedText	json format right text encoded by Base64
	 * @return		text entity that stored in database
	 * @throws Exception
	 */
	@ResponseStatus(value=HttpStatus.CREATED)
	@RequestMapping(value="/v1/diff/{id}/right",method=RequestMethod.PUT)
	public Text saveRight(@PathVariable("id") Long id,@RequestBody String encodedText) throws Exception{
		
		Text text = Base64JsonUtil.decodeToBean(encodedText, Text.class);
		text.setId(id);
		
		return diffedService.saveRight(text);
    }
	
	/**
	 * give diffed info between left text and right text by given text id
	 * 
	 * @param id	text id
	 * @return		detail diffed info
	 * @see waes.task.vo.DiffResult
	 * @throws Exception	
	 * 		when text with given id not existed, throws PreconditionException<br>
	 * 		when left text or right text is missing, throws PreconditionException
	 */
	@ResponseStatus(value=HttpStatus.OK)
	@RequestMapping(value="/v1/diff/{id}",method=RequestMethod.GET)
	public DiffResult getDiffed(@PathVariable("id") Long id) throws Exception{
		
		return diffedService.getDiffed(id);
    }
		
	/**
	 * when PreconditionException happened in these rest endpoints,<br>
	 * give status code (428 Precondition Required) and error message in json format to client
	 * 
	 * @param e	PreconditionException
	 * @return error message
	 * @see waes.task.vo.ErrorInfo
	 */
	@ExceptionHandler(value = PreconditionException.class)
	@ResponseStatus(value=HttpStatus.PRECONDITION_REQUIRED)
	@ResponseBody
	public ErrorInfo errorResponse(Exception e){
 
		ErrorInfo error = new ErrorInfo();
		error.setCode(HttpStatus.PRECONDITION_REQUIRED.value());		
		error.setException(e.getClass().getName());
		error.setMessage(e.getMessage());
		
		return error;
	}
		
}
