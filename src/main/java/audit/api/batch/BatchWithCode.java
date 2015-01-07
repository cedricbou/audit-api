package audit.api.batch;



public class BatchWithCode extends Batch {

	public final BatchCode code;
	
	public BatchWithCode(final Batch batch, final BatchCode code) {
		super( batch.date, batch.entries);
		this.code = code;
	}
	
}
