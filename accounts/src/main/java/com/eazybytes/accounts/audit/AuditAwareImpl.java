package com.eazybytes.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*Con questa classe(bean) intendiamo popolare automaticamente i metadati updatedBy e createdBy della classe BaseEntity
* Per fare questo dobbiamo implementare l'interfaccia AuditorAware che prende un Generic che nel nostro caso
* sarà String che è il tipo di updatedBy e createedBy e anche updatedAt e createdAt saranno formattate come stringhe.*/
@Component(value = "auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");//sta per Accounts Micro Service ed è il valore che vogliamo sia assegnato hai campi updatedBy e createdBy
    }
}
