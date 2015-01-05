package audit.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;

import audit.api.AuditBatch;
import audit.api.AuditBatchEntry;
import audit.api.Audit;
import audit.api.AuditBatchEntry.ControlStatus;
import audit.api.response.AuditBatchReport;

import com.google.common.collect.ImmutableList;

public class AuditInMemoryRepository implements AuditRepository {

	private final Map<String, Audit> controllers = new HashMap<String, Audit>();

	
	private final Map<String, Map<String, List<AuditBatchEntry>>> batches = new HashMap<String, Map<String, List<AuditBatchEntry>>>();

	public void declareController(final Audit controller) {
		this.controllers.put(controller.id, controller);
	}

	public void recordBatchEntry(String controllerId, String batchId,
			AuditBatchEntry entry) {

		if (!batches.containsKey(controllerId)) {
			batches.put(controllerId, new HashMap<String, List<AuditBatchEntry>>());
		}
		
		if(!batches.get(controllerId).containsKey(batchId)) {
			batches.get(controllerId).put(batchId, new LinkedList<AuditBatchEntry>());
		}

		batches.get(controllerId).get(batchId).add(entry);
	}

	public List<Audit> controllers() {
		return ImmutableList.<Audit>builder().addAll(controllers.values()).build();
	}
	
	public ImmutableList<AuditBatchReport> controlBatchSimpleReport(
			final String controllerId) {
		
		final Map<String, List<AuditBatchEntry>> entries = batches.get(controllerId);
		
		if(entries == null) {
			return ImmutableList.of();
		}
		
		final List<AuditBatch> batches = new ArrayList<AuditBatch>(entries.keySet().size());
		
		for(final String id : entries.keySet()) {
			batches.add(new AuditBatch(id, LocalDateTime.now(), entries.get(id)));
		}
				
		final List<AuditBatchReport> reports = new LinkedList<AuditBatchReport>();
		
		for(final AuditBatch batch : batches) {
			long failures = 0;
			long success = 0;
			for(final AuditBatchEntry entry : batch.entries) {
				if(entry.status == ControlStatus.APPROVED) {
					++success;
				}
				else {
					++failures;
				}
			}
			reports.add(new AuditBatchReport(batch.date, failures, success));
		}
		
		return ImmutableList.<AuditBatchReport>builder().addAll(reports).build();
	}
	
}
