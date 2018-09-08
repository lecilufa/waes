package waes.task.exception;

public class PreconditionException extends RuntimeException {

	private static final long serialVersionUID = 787605907590736594L;
	
	public PreconditionException(){
		super();
	}

	public PreconditionException(String msg){
		super(msg);
	}
}
