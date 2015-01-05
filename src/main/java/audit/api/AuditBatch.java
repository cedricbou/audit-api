package audit.api;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuditBatch implements Comparable<AuditBatch> {

	public final String id;
	public final LocalDateTime date;
	public final List<AuditBatchEntry> entries;
	
	@JsonCreator
	public AuditBatch(
		@JsonProperty("id") final String id,
		@JsonProperty("date") final LocalDateTime date, 
		@JsonProperty("entries") final List<AuditBatchEntry> entries) {
		this.date = date;
		this.entries = entries;
		this.id = id;
	}
	
	public int compareTo(AuditBatch o) {
		return date.compareTo(o.date);
	}
}
