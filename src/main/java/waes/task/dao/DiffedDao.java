package waes.task.dao;

import org.apache.ibatis.annotations.Mapper;

import waes.task.model.Text;

@Mapper
public interface DiffedDao {

	Long saveText(Text text);
	
	Long updateText(Text text);

	Text getTextById(Long id);
	
}
