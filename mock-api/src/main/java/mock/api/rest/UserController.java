package mock.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mock.api.model.User;
import mock.api.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController extends BaseCrudController<User, Long, UserRepository> {
	
	@Override
	protected void updateImpl(User entity, User newEntity) {
		entity.setName(newEntity.getName());
	}
	
}