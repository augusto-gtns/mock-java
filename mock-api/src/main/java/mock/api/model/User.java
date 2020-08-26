package mock.api.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User implements BaseEntity, ClientDetails, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@JsonIgnore
	private String password;

	@NotNull
	private String login;

	@NotNull
	private boolean ativo;

	@JsonIgnore
	@Column(insertable = false)
	private Date dtinsert;

	// oauth2

	@Override
	@JsonIgnore
	public String getClientId() {
		return getId().toString();
	}

	@Override
	@JsonIgnore
	public Set<String> getResourceIds() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isSecretRequired() {
		return false;
	}

	@Override
	@JsonIgnore
	public String getClientSecret() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isScoped() {
		return false;
	}

	@Override
	@JsonIgnore
	public Set<String> getScope() {
		return null;
	}

	@Override
	@JsonIgnore
	public Set<String> getAuthorizedGrantTypes() {
		return null;
	}

	@Override
	@JsonIgnore
	public Set<String> getRegisteredRedirectUri() {
		return null;
	}

	@Override
	@JsonIgnore
	public Collection<GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	@JsonIgnore
	public Integer getAccessTokenValiditySeconds() {
		return null;
	}

	@Override
	@JsonIgnore
	public Integer getRefreshTokenValiditySeconds() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

	@Override@JsonIgnore
	public String getUsername() {
		return getLogin();
	}

	@Override@JsonIgnore
	public boolean isAccountNonExpired() {
		return isAtivo();
	}

	@Override@JsonIgnore
	public boolean isAccountNonLocked() {
		return isAtivo();
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return isAtivo();
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return isAtivo();
	}

}
