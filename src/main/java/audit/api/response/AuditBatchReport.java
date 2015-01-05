package audit.api.response;

import org.joda.time.LocalDateTime;

public class AuditBatchReport {

	public final LocalDateTime launch;
	public final long success;
	public final long failures;
	
	public AuditBatchReport(final LocalDateTime launch, final long failures, final long success) {
		this.launch = launch;
		this.success = success;
		this.failures = failures;
	}
}
