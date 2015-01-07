package audit.api.response;

import org.joda.time.LocalDateTime;

public class BatchReport {

	public final String batchCode;
	public final LocalDateTime launch;
	public final long success;
	public final long failures;
	
	public BatchReport(final String batchCode, final LocalDateTime launch, final long failures, final long success) {
		this.launch = launch;
		this.success = success;
		this.failures = failures;
		this.batchCode = batchCode;
	}
}
