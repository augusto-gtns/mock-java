package mock.api.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mock.api.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	User findByLogin(String login);

}