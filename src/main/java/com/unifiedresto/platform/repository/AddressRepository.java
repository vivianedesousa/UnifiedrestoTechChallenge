package com.unifiedresto.platform.repository;
import com.unifiedresto.platform.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
 }
