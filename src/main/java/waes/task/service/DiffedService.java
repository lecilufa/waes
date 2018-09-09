package waes.task.service;

import waes.task.model.Text;
import waes.task.vo.DiffResult;

public interface DiffedService {

	/**
	 * save or update left text, if not existed create one, if existed, update it
	 * 
	 * @param text	text entity
	 * @return
	 * 		text entity that stored in database
	 */
	Text saveLeft(Text text);

	/**
	 * save or update right text, if not existed create one, if existed, update it
	 * 
	 * @param text	text entity
	 * @return
	 * 		text entity that stored in database
	 */
	Text saveRight(Text text);

	/**
	 * get text entity by id
	 * 
	 * @param id	text id
	 * @return
	 * 		text entity with given text id
	 */
	Text getTextById(Long id);

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
	DiffResult getDiffed(Long id) throws Exception;
}
