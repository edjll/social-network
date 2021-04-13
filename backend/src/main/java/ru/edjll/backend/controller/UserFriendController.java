package ru.edjll.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.edjll.backend.dto.user.friend.UserFriendDtoForSave;
import ru.edjll.backend.dto.user.info.UserInfoDtoForFriendsPage;
import ru.edjll.backend.service.UserFriendService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserFriendController {

    private final UserFriendService userFriendService;

    public UserFriendController(UserFriendService userFriendService) {
        this.userFriendService = userFriendService;
    }

    @GetMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserInfoDtoForFriendsPage> getUsers(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String userId,
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Long> countryId,
            @RequestParam Optional<Long> cityId
    ) {
        return userFriendService.getFriends(page, size, userId, firstName, lastName, countryId, cityId);
    }

    @GetMapping("/subscribers")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserInfoDtoForFriendsPage> getSubscribersForUserPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String userId,
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Long> countryId,
            @RequestParam Optional<Long> cityId
    ) {
        return userFriendService.getSubscribers(page, size, userId, firstName, lastName, countryId, cityId);
    }

    @PostMapping("/friend/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserFriendDtoForSave userFriendDtoForSave, Principal principal) {
        this.userFriendService.save(userFriendDtoForSave, principal);
    }

    @PutMapping("/friend/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserFriendDtoForSave userFriendDtoForSave, Principal principal) {
        this.userFriendService.save(userFriendDtoForSave, principal);
    }

    @DeleteMapping("/friend/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestParam String userId, Principal principal) {
        this.userFriendService.delete(userId, principal);
    }
}
