package com.user.subscriber.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="users")
public class User {

	@Id
	private String userId;
	private String userName;
	private String address;
	private List<String> subscribers;
	private List<String> subscriptions;
}
