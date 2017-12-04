package org.apache.pi.rover.rover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@SpringBootApplication
public class RoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoverApplication.class, args);
	}

	@GetMapping
	public String drive(@RequestParam String direction){
		return direction;
	}
}
