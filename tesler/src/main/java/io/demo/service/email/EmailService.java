package io.demo.service.email;

import io.demo.conf.email.EmailProperties;
import io.demo.entity.enums.RequestType;
import io.demo.entity.enums.ShortUrlType;
import io.demo.entity.requestdatabytype.RequestData;
import io.demo.service.common.ShortUrlService;
import io.tesler.model.core.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	private final EmailProperties emailProperties;

	private final ShortUrlService shortUrlService;

	private final SpringTemplateEngine templateEngine;

	private final ResourceLoader resourceLoader;


	@SneakyThrows
	public void completeRequestNotification(@NotNull RequestData requestData) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		Context context = new Context();
		context.setVariables(Map.of(
				"implementer", ofNullable(requestData.getImplementer()).map(User::getFullUserName).orElse("-"),
				"requestType", requestData.getType().getValue(),
				"requestLink", shortUrlService.upsertShortUrl(requestData, ShortUrlType.FORM_REQUEST)
		));
		String template = templateEngine.process("ESMCompleteNotification.html", context);
		helper.setTo(ofNullable(requestData.getUser()).map(User::getEmail).orElseThrow());
		helper.setFrom(emailProperties.getFrom());
		helper.setSubject("Выполнение заявки");
		helper.setText(template, true);
		javaMailSender.send(mimeMessage);
	}

	@SneakyThrows
	public void needInfoNotification(@NotNull RequestData requestData) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		Context context = new Context();
		context.setVariables(Map.of(
				"implementer", ofNullable(requestData.getImplementer()).map(User::getFullUserName).orElse("-"),
				"requestType", requestData.getType().getValue(),
				"requestLink", shortUrlService.upsertShortUrl(requestData, ShortUrlType.FORM_REQUEST)
		));
		String template = templateEngine.process("ESMNeedInfoNotification.html", context);
		helper.setTo(ofNullable(requestData.getUser()).map(User::getEmail).orElseThrow());
		helper.setFrom(emailProperties.getFrom());
		helper.setSubject("Требуется информация");
		helper.setText(template, true);
		javaMailSender.send(mimeMessage);
	}

	@SneakyThrows
	public void rejectNotification(@NotNull RequestData requestData, @NotNull String comment) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		Context context = new Context();
		context.setVariables(Map.of(
				"implementer", ofNullable(requestData.getImplementer()).map(User::getFullUserName).orElse("-"),
				"requestType", requestData.getType().getValue(),
				"requestLink", shortUrlService.upsertShortUrl(requestData, ShortUrlType.FORM_REQUEST),
				"reason", comment
		));
		String template = templateEngine.process("ESMRejectNotification.html", context);
		helper.setTo(ofNullable(requestData.getUser()).map(User::getEmail).orElseThrow());
		helper.setFrom(emailProperties.getFrom());
		helper.setSubject("Отклонение заявки");
		helper.setText(template, true);
		javaMailSender.send(mimeMessage);
	}

	@SneakyThrows
	public void commentAddNotification(@NotNull RequestData requestData, @NotNull String comment) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		Context context = new Context();
		context.setVariables(Map.of(
				"implementer", ofNullable(requestData.getImplementer()).map(User::getFullUserName).orElse("-"),
				"requestType", requestData.getType().getValue(),
				"requestLink", shortUrlService.upsertShortUrl(requestData, ShortUrlType.FORM_REQUEST),
				"comment", comment
		));
		String template = templateEngine.process("ESMCommentAddNotification.html", context);
		helper.setTo(ofNullable(requestData.getUser()).map(User::getEmail).orElseThrow());
		helper.setFrom(emailProperties.getFrom());
		helper.setSubject("Комментарий к заявке");
		helper.setText(template, true);
		javaMailSender.send(mimeMessage);
	}

	@SneakyThrows
	public void addFilesToRequestByImplementerEmployeeNotification(@NotNull RequestData requestData, @NonNull ByteArrayOutputStream zip) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		Context context = new Context();
		context.setVariables(Map.of(
				"implementer", ofNullable(requestData.getImplementer()).map(User::getFullUserName).orElse("-"),
				"requestType", requestData.getType().getValue(),
				"requestLink", shortUrlService.upsertShortUrl(requestData, ShortUrlType.FORM_REQUEST)
		));
		String template = templateEngine.process("ESMAddFileNotification.html", context);

		helper.setTo(ofNullable(requestData.getUser()).map(User::getEmail).orElseThrow());
		helper.setFrom(emailProperties.getFrom());
		helper.setSubject("Добавление файла в заявку");
		helper.setText(template, true);

		String zipName = ofNullable(requestData.getType()).map(RequestType::getValue).orElse("Заявка");
		helper.addAttachment(zipName + ".zip", new ByteArrayDataSource(zip.toByteArray(), "application/zip"));

		javaMailSender.send(mimeMessage);
	}



}
