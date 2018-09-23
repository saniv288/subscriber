package com.user.subscriber.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.subscriber.model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
