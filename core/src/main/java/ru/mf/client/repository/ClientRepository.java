package ru.mf.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.mf.client.model.Client;


public interface ClientRepository extends JpaRepository<Client, Long>,
        JpaSpecificationExecutor<Client> {
}