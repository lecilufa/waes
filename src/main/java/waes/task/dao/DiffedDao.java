package waes.task.dao;

import org.apache.ibatis.annotations.Mapper;

import waes.task.model.Text;

@Mapper
public interface DiffedDao {

	/**
	 * save text
	 * 
	 * @param text text entity
	 * @return
	 * 		how many records being saved
	 */
	Long saveText(Text text);
	
	/**
	 * update text
	 * 
	 * @param text	text entity
	 * @return
	 * 		how many records being updated
	 */
	Long updateText(Text text);

	/**
	 * get text entity by id
	 * 
	 * @param id	text id
	 * @return
	 * 		text entity with given text id
	 */
	Text getTextById(Long id);
	
}
