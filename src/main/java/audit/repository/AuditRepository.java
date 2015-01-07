package audit.repository;

import java.util.List;

import audit.api.audit.Audit;
import audit.api.audit.AuditCode;
import audit.api.audit.AuditWithCode;

import com.google.common.base.Optional;

public interface AuditRepository {

	public void save(final AuditWithCode audit);
	
	public Optional<Audit> find(final AuditCode code);

	public List<AuditWithCode> all();
}
