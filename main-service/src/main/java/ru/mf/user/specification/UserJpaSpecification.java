package ru.mf.user.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.mf.user.UserDeleteDto;
import ru.mf.user.model.User;

import java.util.Arrays;
import java.util.List;


@Component
public class UserJpaSpecification {
    public Specification<User> searchSpec(String filterText) {
        return searchOr(filterText, Arrays.asList("first_name", "last_name", "email"));
    }

    private static Specification<User> searchOr(String filterText, List<String> fields) {
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

    public Specification<User> deleteUserSpec(UserDeleteDto dto) {
        return buildSpecification(dto.getFirstName(), dto.getLastName(), dto.getEmail());
    }

    private static Specification<User> buildSpecification(String firstName, String lastName, String email) {
        return (root, query, cb) -> {
            if ((firstName == null || firstName.trim().isEmpty()) &&
                    (lastName == null || lastName.trim().isEmpty()) &&
                    (email == null || email.trim().isEmpty())) {
                return cb.conjunction();
            }

            String firstNamePattern = "%" + (firstName != null ? firstName.trim().toLowerCase() : "") + "%";
            String lastNamePattern = "%" + (lastName != null ? lastName.trim().toLowerCase() : "") + "%";
            String emailPattern = "%" + (email != null ? email.trim().toLowerCase() : "") + "%";

            return cb.and(
                    firstName != null && !firstName.trim().isEmpty() ? cb.like(cb.lower(root.get("first_name")),
                            firstNamePattern) : cb.conjunction(),
                    lastName != null && !lastName.trim().isEmpty() ? cb.like(cb.lower(root.get("last_name")),
                            lastNamePattern) : cb.conjunction(),
                    email != null && !email.trim().isEmpty() ? cb.like(cb.lower(root.get("email")),
                            emailPattern) : cb.conjunction()
            );
        };
    }

}
