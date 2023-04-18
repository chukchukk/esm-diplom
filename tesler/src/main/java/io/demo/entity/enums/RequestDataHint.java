package io.demo.entity.enums;

import io.demo.entity.requestdatabytype.RequestData;
import io.demo.entity.requestdatabytype.personaldatachange.DocumentsChangeRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Getter
@AllArgsConstructor
public enum RequestDataHint {
	PASSPORT_CHANGE_NEED_INFO(Pair.of(RequestType.PASSPORT_CHANGE, RequestStatus.NEED_INFO),
			(requestData -> null)
	),
	DOCUMENTS_CHANGE_NEED_INFO(Pair.of(RequestType.DOCUMENTS_CHANGE, RequestStatus.NEED_INFO),
			(requestData -> {
				DocumentsChangeRequestData castedData = (DocumentsChangeRequestData) requestData;
				if (ofNullable(castedData.getSnilsChangeBySelf()).orElse(false)) {
					return HintText.SNILS + HintText.SEND_TO_WORK_INFO;
				}
				return null;
			})
	),
	PHONE_NUMBER_CHANGE_NEED_INFO(Pair.of(RequestType.PHONE_NUMBER_CHANGE, RequestStatus.NEED_INFO),
			(requestData -> null)
	);

	private final Pair<RequestType, RequestStatus> neededTypeWithStatus;

	private final Function<RequestData, String> hintText;

	public static Optional<RequestDataHint> getHintByRequestTypeAndStatus(RequestType type, RequestStatus status) {
		return Arrays.stream(RequestDataHint.values())
				.filter(requestDataHint -> type.equals(requestDataHint.getNeededTypeWithStatus().getLeft()) && status.equals(requestDataHint.getNeededTypeWithStatus().getRight()))
				.findFirst();
	}

	@Getter
	private static class HintText {
		private static final String SNILS = "Ранее ты отметил(-а), что самостоятельно перевыпустишь СНИЛС. Тебе необходимо добавить скан перевыпущенного СНИЛС с новой фамилией в «Файлы заявителя», " +
				"чтобы мы смогли выполнить заявку.\n\n";

		private static final String SEND_TO_WORK_INFO = "После загрузки всех необходимых файлов нажми на кнопку «Вернуть в работу», чтобы заявка вернулась к исполнителю.";

	}

}
