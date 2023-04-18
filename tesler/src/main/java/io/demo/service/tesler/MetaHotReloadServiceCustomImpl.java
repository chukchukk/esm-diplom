package io.demo.service.tesler;


import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.service.session.InternalAuthorizationService;
import io.tesler.api.service.tx.TransactionService;
import io.tesler.core.metahotreload.MetaHotReloadService;
import io.tesler.core.metahotreload.conf.properties.MetaConfigurationProperties;
import io.tesler.core.metahotreload.dto.BcSourceDTO;
import io.tesler.core.metahotreload.dto.ScreenSourceDto;
import io.tesler.core.metahotreload.dto.ViewSourceDTO;
import io.tesler.core.metahotreload.dto.WidgetSourceDTO;
import io.tesler.core.metahotreload.service.*;
import io.tesler.model.core.dao.JpaDao;
import io.tesler.model.core.entity.Responsibilities;
import io.tesler.model.ui.entity.*;
import io.tesler.model.ui.navigation.NavigationGroup;
import io.tesler.model.ui.navigation.NavigationView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static io.tesler.api.service.session.InternalAuthorizationService.VANILLA;

@Primary
@Service
@RequiredArgsConstructor
public class MetaHotReloadServiceCustomImpl implements MetaHotReloadService {

	protected final MetaConfigurationProperties config;

	protected final MetaResourceReaderService metaResourceReaderService;

	protected final InternalAuthorizationService authzService;

	protected final TransactionService txService;

	protected final JpaDao jpaDao;

	protected final WidgetUtil widgetUtil;

	protected final ViewAndViewWidgetUtil viewAndViewWidgetUtil;

	protected final ScreenAndNavigationGroupAndNavigationViewUtil screenAndNavigationGroupAndNavigationViewUtil;

	protected final BcUtil bcUtil;

	private static void deleteAllMeta(@NotNull JpaDao jpaDao) {
		jpaDao.delete(NavigationView.class, (root, query, cb) -> cb.and());
		jpaDao.delete(NavigationGroup.class, (root, query, cb) -> cb.and());
		jpaDao.delete(Screen.class, (root, query, cb) -> cb.and());
		jpaDao.delete(ViewWidgets.class, (root, query, cb) -> cb.and());
		jpaDao.delete(View.class, (root, query, cb) -> cb.and());
		jpaDao.delete(Widget.class, (root, query, cb) -> cb.and());
		jpaDao.delete(Bc.class, (root, query, cb) -> cb.and());
	}

	public void loadMeta() {
		List<BcSourceDTO> bcDtos = metaResourceReaderService.getBcs();
		List<ScreenSourceDto> screenDtos = metaResourceReaderService.getScreens();
		List<WidgetSourceDTO> widgetDtos = metaResourceReaderService.getWidgets();
		List<ViewSourceDTO> viewDtos = metaResourceReaderService.getViews();

		authzService.loginAs(authzService.createAuthentication(VANILLA));

		txService.invokeInTx(() -> {
			loadMetaPreProcess(widgetDtos, viewDtos, screenDtos);
			deleteAllMeta(jpaDao);
			bcUtil.process(bcDtos);
			Map<String, Widget> nameToWidget = widgetUtil.process(widgetDtos);
			viewAndViewWidgetUtil.process(viewDtos, nameToWidget);
			screenAndNavigationGroupAndNavigationViewUtil.process(screenDtos);
			responsibilitiesProcess(screenDtos, viewDtos);
			loadMetaAfterProcess();
			return null;
		});
	}

	private void responsibilitiesProcess(List<ScreenSourceDto> screenDtos, List<ViewSourceDTO> viewDtos) {
		if (config.isViewAllowedRolesEnabled()) {
			Map<String, String> viewToScreenMap = jpaDao
					.getList(NavigationView.class)
					.stream()
					.collect(Collectors.toMap(NavigationView::getViewName, NavigationView::getScreenName, (first, second) -> first));

			List<Responsibilities> responsibilities = new ArrayList<>();
			long defaultDepartmentId = 0L;
			viewDtos.forEach(view -> {
				view.getRolesAllowed().forEach(role -> {
					responsibilities.add(new Responsibilities()
							.setResponsibilityType(Responsibilities.ResponsibilityType.VIEW)
							.setInternalRoleCD(new LOV(role))
							.setView(view.getName())
							.setDepartmentId(defaultDepartmentId));
				});
			});

			Map<String, ScreenSourceDto> screenNameToScreen = screenDtos.stream()
					.collect(Collectors.toMap(ScreenSourceDto::getName, sd -> sd));

			Map<String, Set<ScreenSourceDto>> rolesToScreens = new HashMap<>();
			viewDtos.forEach(v -> {
				if (viewToScreenMap.containsKey(v.getName())) {
					String screenName = viewToScreenMap.get(v.getName());
					v.getRolesAllowed().forEach(role -> {
						if (!rolesToScreens.containsKey(role)) {
							rolesToScreens.put(role, new HashSet<>());
						}
						rolesToScreens.get(role).add(screenNameToScreen.get(screenName));
					});
				}
			});

			for (Map.Entry<String, Set<ScreenSourceDto>> entry : rolesToScreens.entrySet()) {
				String role = entry.getKey();
				Set<ScreenSourceDto> screens = entry.getValue();
				responsibilities.add(new Responsibilities()
						.setResponsibilityType(Responsibilities.ResponsibilityType.SCREEN)
						.setInternalRoleCD(new LOV(role))
						.setScreens(mapToScreens(screenNameToScreen, screens))
						.setDepartmentId(defaultDepartmentId));
			}

			jpaDao.delete(Responsibilities.class, (root, query, cb) -> cb.and());
			jpaDao.saveAll(responsibilities);

		}
	}

	@NonNull
	private String mapToScreens(@NonNull Map<String, ScreenSourceDto> screenNameToScreen,
								@NonNull Set<ScreenSourceDto> screens) {
		StringJoiner joiner = new StringJoiner(",");
		List<ScreenSourceDto> orderedScreens = screens
				.stream()
				.sorted(Comparator.comparing(ScreenSourceDto::getOrder))
				.collect(Collectors.toList());
		for (int i = 0; i < orderedScreens.size(); i++) {
			ScreenSourceDto screen = orderedScreens.get(i);
			String s = "  {\n"
					+ "    \"id\": \"id" + i + "\",\n"
					+ "    \"name\": \"" + screen.getName() + "\",\n"
					+ "    \"text\": \"" + screen.getTitle() + "\",\n"
					+ "    \"url\": \"/screen/" + screen.getName() + "\",\n"
					+ "    \"icon\": \"" + screen.getIcon() + "\"\n"
					+ "  }";
			joiner.add(s);
		}
		String collect = joiner.toString();
		return "[\n" + collect + "\n]";
	}

	protected void loadMetaPreProcess(List<WidgetSourceDTO> widgetDtos,
										List<ViewSourceDTO> viewDtos,
										List<ScreenSourceDto> screenDtos) {

	}

	protected void loadMetaAfterProcess() {

	}

}
