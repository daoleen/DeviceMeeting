package com.daoleen.devicemeeting.web.repository;

import com.daoleen.devicemeeting.web.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by alex on 17.6.14.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
