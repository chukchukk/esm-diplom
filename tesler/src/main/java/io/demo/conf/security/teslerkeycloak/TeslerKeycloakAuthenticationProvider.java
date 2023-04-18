package io.demo.conf.security.teslerkeycloak;

import io.demo.conf.tesler.dictionary.Dictionary.UserRoleEnum;
import io.demo.repository.DepartmentRepository;
import io.demo.repository.UserRepository;
import io.demo.repository.UserRoleRepository;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.service.session.InternalAuthorizationService;
import io.tesler.api.service.tx.TransactionService;
import io.tesler.core.util.SQLExceptions;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.model.core.entity.User;
import io.tesler.model.core.entity.UserRole;
import io.tesler.model.core.entity.User_;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LockOptions;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static io.tesler.api.service.session.InternalAuthorizationService.VANILLA;

@Component
@Slf4j
public class TeslerKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {

	public static final String UUID_LDAP_ATTRIBUTE = "uuidLdapAttribute";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private JpaDao jpaDao;

	@Autowired
	private TransactionService txService;

	@Autowired
	private InternalAuthorizationService authzService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication auth = super.authenticate(authentication);
		KeycloakAuthenticationToken accessToken = (KeycloakAuthenticationToken) auth;
		SimpleKeycloakAccount account = (SimpleKeycloakAccount) accessToken.getDetails();

		txService.invokeInTx(() -> {
			upsertUserAndRoles(
					account.getKeycloakSecurityContext().getToken()
			);
			return null;
		});

		return authentication;
	}

	private void upsertUserAndRoles(AccessToken accessToken) {
		try {
			txService.invokeInNewTx(() -> {
				authzService.loginAs(authzService.createAuthentication(VANILLA));
				User user = null;
				try {
					String email = accessToken.getEmail();
					user = getUserByEmail(email);
					if (user == null) {
						upsert(accessToken, "EMPLOYEE");
					}
					user = getUserByEmail(email);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage(), e);
				}

				if (user == null) {
					throw new UsernameNotFoundException(null);
				}
				SecurityContextHolder.getContext().setAuthentication(null);
				return null;
			});
		} catch (DataIntegrityViolationException ex) {
			log.error(ex.getLocalizedMessage(), ex);
		}
	}

	public User upsert(AccessToken accessToken, String role) {
		txService.invokeInNewTx(() -> {
					String email = accessToken.getEmail();
					authzService.loginAs(authzService.createAuthentication(VANILLA));
					for (int i = 1; i <= 10; i++) {
						User existing = getUserByEmail(email);
						if (existing != null) {
							jpaDao.lockAndRefresh(existing, LockOptions.WAIT_FOREVER);
							updateUser(accessToken, role, existing);
							return existing;
						}
						try {
							User newUser = new User();
							updateUser(accessToken, role, newUser);
							Long id = txService.invokeNoTx(() -> userRepository.save(newUser).getId());
							UserRole newUserRole = new UserRole();
							newUserRole.setUser(newUser);
							newUserRole.setInternalRoleCd(UserRoleEnum.EMPLOYEE.getLov());
							newUserRole.setActive(true);
							newUserRole.setMain(true);
							userRoleRepository.save(newUserRole);
							return userRepository.findById(id);
						} catch (Exception ex) {
							if (SQLExceptions.isUniqueConstraintViolation(ex)) {
								log.error(ex.getLocalizedMessage(), ex);
							} else {
								throw ex;
							}
						}
					}
					SecurityContextHolder.getContext().setAuthentication(null);
					return null;
				}
		);
		return null;
	}

	private User getUserByEmail(String email) {
		return userRepository.findOne(
				(root, cq, cb) -> cb.equal(root.get(User_.email), email)
		).orElse(null);
	}

	private void updateUser(AccessToken accessToken, String role, User user) {
		String uuidLdapAttribute = (String) accessToken.getOtherClaims().get(UUID_LDAP_ATTRIBUTE);
		user.setDn(uuidLdapAttribute);
		user.setLogin(accessToken.getName().toUpperCase());
		user.setInternalRole(new LOV(role));
		user.setUserPrincipalName(accessToken.getName());
		user.setFirstName(accessToken.getGivenName());
		user.setLastName(accessToken.getFamilyName());
		user.setTitle(accessToken.getName());
		user.setFullUserName(accessToken.getName());
		user.setEmail(accessToken.getEmail());
		user.setPhone(accessToken.getPhoneNumber());
		user.setActive(true);
		user.setDepartment(departmentRepository.findById(0L).orElse(null));
	}

}
