package audit.api;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class AuditBatchEntry {

	public static enum ControlStatus {
		APPROVED, MISSING, DISAPPROVED		
	}
	
	public final String ref1;
	public final Optional<String> ref2;
	public final Optional<String> ref3;
	public final ControlStatus status;
	public final LocalDateTime since;
	
	@JsonCreator
	public AuditBatchEntry(
		@JsonProperty("ref1") final String ref1,
		@JsonProperty("ref2") final Optional<String> ref2,
		@JsonProperty("ref3") final Optional<String> ref3,
		@JsonProperty("status") final ControlStatus status,
		@JsonProperty("since") final LocalDateTime since) {

		super();
		this.ref1 = ref1;
		this.ref2 = ref2;
		this.ref3 = ref3;
		this.status = status;
		this.since = since;
	}
	
}
