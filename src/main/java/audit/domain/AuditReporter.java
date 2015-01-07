package audit.domain;

import java.util.LinkedList;
import java.util.List;

import audit.api.batch.BatchEntry;
import audit.api.batch.BatchEntry.ControlStatus;
import audit.api.batch.BatchWithCode;
import audit.api.response.BatchReport;

import com.google.common.collect.ImmutableList;

public class AuditReporter {

	
	public ImmutableList<BatchReport> simpleReport(final List<BatchWithCode> batches) {
		
		if(batches == null) {
			return ImmutableList.of();
		}
				
		final List<BatchReport> reports = new LinkedList<BatchReport>();
		
		for(final BatchWithCode batch : batches) {
			long failures = 0;
			long success = 0;
			for(final BatchEntry entry : batch.entries) {
				if(entry.status == ControlStatus.APPROVED) {
					++success;
				}
				else {
					++failures;
				}
			}
			reports.add(new BatchReport(batch.code.code, batch.date, failures, success));
		}
		
		return ImmutableList.<BatchReport>builder().addAll(reports).build();
	}

}
