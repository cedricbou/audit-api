package audit.repository;

import java.util.List;

import audit.api.AuditBatchEntry;
import audit.api.Audit;
import audit.api.response.AuditBatchReport;

import com.google.common.collect.ImmutableList;

public interface AuditRepository {

	public void declareController(final Audit controller);
	
	public void recordBatchEntry(final String controllerId, final String batchId, final AuditBatchEntry entry);
	
	public ImmutableList<AuditBatchReport> controlBatchSimpleReport(final String controllerId);

	public List<Audit> controllers();
}
