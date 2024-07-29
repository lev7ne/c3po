package ru.mf.client.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.mf.client.model.ClientMo;

import java.util.Arrays;
import java.util.List;


@Component
public class ClientMoJpaSpecification {
    public Specification<ClientMo> searchSpec(String filterText) {
        return searchOr(filterText, Arrays.asList("org_name", "inn", "tenant",
                "personal_account", "msisdn", "vip"));
    }

    private static Specification<ClientMo> searchOr(String filterText, List<String> fields) {
        return (root, query, cb) -> {
            if (filterText == null || filterText.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + filterText.trim().toLowerCase() + "%";
            return fields.stream()
                    .map(field -> cb.like(cb.lower(root.get(field)), likePattern))
                    .reduce(cb::or)
                    .orElse(cb.conjunction());
        };
    }
}
