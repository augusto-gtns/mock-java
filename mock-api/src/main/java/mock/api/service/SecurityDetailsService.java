package mock.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import mock.api.repository.UserRepository;

@Service
public class SecurityDetailsService implements UserDetailsService, ClientDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return userRepository.findById(Long.parseLong(clientId)).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByLogin(username);
	}
}
