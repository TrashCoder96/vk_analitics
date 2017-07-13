package ru.vk.analitics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.analitics.data.User;
import ru.vk.analitics.data.dao.UserRepositoty;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VkAnaliticsApplication {

	@Autowired
	private UserRepositoty userRepositoty;

	@PostConstruct
	@Transactional
	private void post() {
		User user = userRepositoty.findByLogin("admin");
		if (user == null) {
			User u = new User();
			u.setLogin("admin");
			u.setPassword("admin");
			userRepositoty.save(u);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(VkAnaliticsApplication.class, args);
	}
}
