package com.example.PetFriends_Almoxarifado;

import org.springframework.boot.SpringApplication;

public class TestPetFriendsAlmoxarifadoApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetFriendsAlmoxarifadoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
