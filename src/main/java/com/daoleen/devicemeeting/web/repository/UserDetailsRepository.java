package com.daoleen.devicemeeting.web.repository;

import com.daoleen.devicemeeting.web.domain.UserDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alex on 14.7.14.
 */
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
}
