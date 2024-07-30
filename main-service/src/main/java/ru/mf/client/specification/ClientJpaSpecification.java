package ru.mf.client.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.mf.client.model.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Component
public class ClientJpaSpecification {
    public Specification<Client> searchSpec(String filterText) {
        return searchOr(filterText, Arrays.asList("orgName", "inn", "tenant",
                "personalAccount", "msisdn", "vip"));
    }

    private static Specification<Client> searchOr(String filterText, List<String> fields) {
        return (root, query, cb) -> {
            if (filterText == null || filterText.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + filterText.trim().toLowerCase() + "%";

            return fields.stream()
                    .map(field -> getFieldAsString(root, cb, field))
                    .filter(Objects::nonNull)
                    .map(field -> cb.like(cb.lower(field), likePattern))
                    .reduce(cb::or)
                    .orElse(cb.conjunction());
        };
    }

    private static Expression<String> getFieldAsString(Root<Client> root, CriteriaBuilder cb, String field) {
        Expression<?> exp = root.get(field);
        if (exp.getJavaType() == String.class) {
            return root.get(field);
        } else if (Number.class.isAssignableFrom(exp.getJavaType())) {
            return cb.toString(root.get(field));
        } else if (Boolean.class.isAssignableFrom(exp.getJavaType())) {
            return cb.toString(root.get(field));
        }
        return null;
    }
}
