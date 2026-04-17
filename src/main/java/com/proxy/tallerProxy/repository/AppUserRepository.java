package com.proxy.tallerProxy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxy.tallerProxy.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByExternalId(String externalId);
}
