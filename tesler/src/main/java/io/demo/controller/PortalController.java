package io.demo.controller;

import io.demo.service.portal.PortalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PortalController {

	private final PortalApiService portalApiService;

	@GetMapping("/shortUrl/{uuid}")
	public ResponseEntity<Void> getExtLink(@NotNull @PathVariable(name = "uuid") String uuid) {
		String redirectUrl = portalApiService.getExtLink(uuid);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(URI.create(redirectUrl));
		return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}

}
