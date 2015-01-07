package audit.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import audit.api.audit.AuditCode;
import audit.api.batch.BatchCode;
import audit.api.batch.BatchWithCode;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class BatchInMemoryRepository implements BatchRepository {
	
	private final Map<BatchCode, BatchWithCode> batches = new HashMap<BatchCode, BatchWithCode>();
	private final Map<AuditCode, List<BatchWithCode>> batchesByAudit = new HashMap<AuditCode, List<BatchWithCode>>();

	@Override
	public List<BatchWithCode> find(AuditCode code) {
		final List<BatchWithCode> batches = batchesByAudit.get(code);
		
		if(batches != null) {
			Collections.sort(batches);
			return ImmutableList.<BatchWithCode>builder().addAll(batches).build();
		}
		else {
			return ImmutableList.of();
		}
	}
	
	@Override
	public Optional<BatchWithCode> find(BatchCode code) {
		return Optional.fromNullable(batches.get(code));
	}
	
	@Override
	public void save(BatchWithCode batch) {
		batches.put(batch.code, batch);
		
		if(!batchesByAudit.containsKey(batch.code.auditCode)) {
			batchesByAudit.put(batch.code.auditCode, new LinkedList<BatchWithCode>());
		}
		
		batchesByAudit.get(batch.code.auditCode).add(batch);
	}
		
}
