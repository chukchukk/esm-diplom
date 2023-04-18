package io.demo.service.common;

import io.tesler.core.crudma.bc.BcIdentifier;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.dto.rowmeta.PostAction;
import lombok.experimental.UtilityClass;

import static io.demo.controller.TeslerRestController.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class PostActions {

	private static final Map<String, String> MAP_TO_IMPLEMENTER_PICKLIST_POPUP = Map.of(
			request.getName(), implementerPickListPopupRequest.getName(),
			requestList.getName(), implementerPickListPopupRequestList.getName(),
			requestNew.getName(), implementerPickListPopupRequestListNew.getName(),
			requestInProgress.getName(), implementerPickListPopupRequestListInProgress.getName()
	);

	public static PostAction showImplementerPickList(BusinessComponent bc) {
		return new PostAction()
				.add("type", "showViewPopup")
				.add("bcName", MAP_TO_IMPLEMENTER_PICKLIST_POPUP.get(bc.getName()))
				.add("widgetName", MAP_TO_IMPLEMENTER_PICKLIST_POPUP.get(bc.getName()))
				.add("implementer", "implementerName")
				.add("implementerId", "id");
	}

	/**
	 * Дополнительное действие, осуществляющее обновление нескольких BcIdentifier
	 * Лист BcIdentifier превращается в строку с делителем ";", которая парсится на фронте
	 *
	 * @param identifierList лист BcIdentifier для обновления (в порядке обновления)
	 * @return Дополнительное действие обновления листа BcIdentifier (усоверщенствованный {@link PostAction.BasePostActionType#REFRESH_BC})
	 */
	public static PostAction refreshMultipleBc(final List<BcIdentifier> identifierList) {
		return new PostAction()
				.add("type", "refreshMultipleBc")
				.add("bcList", identifierList
						.stream()
						.map(BcIdentifier::getName)
						.collect(Collectors.joining(";")));
	}



}
