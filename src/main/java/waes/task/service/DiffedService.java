package waes.task.service;

import waes.task.model.Text;
import waes.task.vo.DiffResult;

public interface DiffedService {

	Text saveLeft(Text text);

	Text saveRight(Text text);

	Text getTextById(Long id);

	DiffResult getDiffed(Long id);
}
