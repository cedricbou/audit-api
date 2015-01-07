package audit.repository;

import java.util.List;

import audit.api.audit.AuditCode;
import audit.api.batch.BatchCode;
import audit.api.batch.BatchWithCode;

import com.google.common.base.Optional;

public interface BatchRepository {

	public void save(final BatchWithCode batch);
	
	public List<BatchWithCode> find(final AuditCode code);
	
	public Optional<BatchWithCode> find(final BatchCode code);
	
}
