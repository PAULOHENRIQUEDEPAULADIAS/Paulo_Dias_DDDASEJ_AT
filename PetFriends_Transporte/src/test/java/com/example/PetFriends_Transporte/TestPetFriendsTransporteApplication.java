package com.example.PetFriends_Transporte;

import org.springframework.boot.SpringApplication;

public class TestPetFriendsTransporteApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetFriendsTransporteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
