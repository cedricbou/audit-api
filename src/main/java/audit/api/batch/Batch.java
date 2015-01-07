package audit.api.batch;

import java.util.List;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Batch implements Comparable<Batch> {

	public final LocalDateTime date;
	public final List<BatchEntry> entries;
	
	@JsonCreator
	public Batch(
		@JsonProperty("date") final LocalDateTime date, 
		@JsonProperty("entries") final List<BatchEntry> entries) {
		this.date = date;
		this.entries = entries;
	}

	public BatchWithCode withCode(final BatchCode code) {
		return new BatchWithCode(this, code);
	}
	
	public int compareTo(Batch o) {
		return date.compareTo(o.date);
	}
}
