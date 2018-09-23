package com.user.subscriber.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.subscriber.model.User;
import com.user.subscriber.repository.UserRepository;

@RestController
@RequestMapping(path = "/user")
public class SubscriberAPIController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		userRepository.save(user);
		return ResponseEntity.ok("successfully created");
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{userId}" + "/subscribe/{subscribedUserId}")
	public ResponseEntity<?> subscribeUser(@PathVariable("userId") String userId,
			@PathVariable("subscribedUserId") String subscribedUserId) {
		if (userId.equals(subscribedUserId))
			return ResponseEntity.badRequest().body("self-subscription not available");
		if (!userRepository.existsById(userId))
			return ResponseEntity.badRequest().body("invalid userId");
		if (!userRepository.existsById(subscribedUserId))
			return ResponseEntity.badRequest().body("subscribed user does not exists");

		Optional<User> result = userRepository.findById(userId);
		User user = result.get();
		Optional<User> resultSubscribed = userRepository.findById(subscribedUserId);
		User subscribed = resultSubscribed.get();
		if (user.getSubscriptions() != null)
			user.getSubscriptions().add(subscribed.getUserId());
		else {
			List<String> subscriptions = new ArrayList<>();
			subscriptions.add(subscribed.getUserId());
			user.setSubscriptions(subscriptions);
		}

		if (subscribed.getSubscribers() != null)
			subscribed.getSubscribers().add(user.getUserId());
		else {
			List<String> subscribers = new ArrayList<>();
			subscribers.add(userId);
			subscribed.setSubscribers(subscribers);
		}

		userRepository.save(user);
		userRepository.save(subscribed);
		return ResponseEntity.ok("subscribed successfully");
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
		if (!userRepository.existsById(userId))
			return ResponseEntity.badRequest().body("invalid userId");
		Optional<User> result = userRepository.findById(userId);
		User user = result.get();
		return ResponseEntity.ok(user);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{userId}/subscribers",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSubscribers(@PathVariable("userId") String userId) {
		if (!userRepository.existsById(userId))
			return ResponseEntity.badRequest().body("invalid userId");
		
		Optional<User> result = userRepository.findById(userId);
		User user = result.get();
		List<String> subscribers=user.getSubscribers();
		List<User> users=null;
		if(subscribers==null||subscribers.isEmpty())
			return ResponseEntity.noContent().build();
		else 
			users=(List<User>) userRepository.findAllById(subscribers);
		return ResponseEntity.ok(users);
	}
}
