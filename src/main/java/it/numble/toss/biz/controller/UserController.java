package it.numble.toss.biz.controller;

import it.numble.toss.biz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

	private final UserService userService;

}
