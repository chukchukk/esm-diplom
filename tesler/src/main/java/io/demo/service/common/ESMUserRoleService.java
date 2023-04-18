package io.demo.service.common;

import io.demo.conf.tesler.dictionary.Dictionary;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.core.util.session.SessionService;
import io.tesler.model.core.entity.User;
import io.tesler.model.core.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ESMUserRoleService {

	private final SessionService sessionService;

	public User getCurrentUser() {
		return sessionService.getSessionUser();
	}

	public boolean currentUserRoleIsEqualsTo(List<Dictionary.UserRoleEnum> list) {
		return list.stream()
				.map(Dictionary.UserRoleEnum::getLov)
				.collect(Collectors.toList())
				.contains(sessionService.getSessionUser().getInternalRole());
	}

	public boolean userHasRole(User user, Dictionary.UserRoleEnum role) {
		return user
				.getUserRoleList()
				.stream()
				.filter(UserRole::getActive)
				.map(UserRole::getInternalRoleCd)
				.anyMatch(r -> role.getLov().equals(r));
	}

	public boolean userHasOneOrAnyOfRoles(User user, List<Dictionary.UserRoleEnum> roles) {
		List<LOV> userRoleLovs = user
				.getUserRoleList()
				.stream()
				.filter(UserRole::getActive)
				.map(UserRole::getInternalRoleCd)
				.collect(Collectors.toList());
		List<LOV> parameterRoles = roles.stream()
				.map(Dictionary.UserRoleEnum::getLov)
				.collect(Collectors.toList());
		return parameterRoles.removeAll(userRoleLovs);
	}

}
