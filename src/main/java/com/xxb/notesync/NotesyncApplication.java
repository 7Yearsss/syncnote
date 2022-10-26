package com.xxb.notesync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class NotesyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesyncApplication.class, args);
	}

}
