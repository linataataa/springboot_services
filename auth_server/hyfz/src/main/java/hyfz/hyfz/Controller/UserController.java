package hyfz.hyfz.Controller;

import hyfz.hyfz.PlayLoad.Requests.LoginRequest;
import hyfz.hyfz.PlayLoad.Requests.RegisterRequest;
import hyfz.hyfz.PlayLoad.Response.UserInfoResponse;
import hyfz.hyfz.Services.UserService;
import hyfz.hyfz.exception.RequestValidationException;
import hyfz.hyfz.user.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //@PreAuthorize("hasRole()")
    @GetMapping("/paginate")
    public Page<User> paginateUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortField", defaultValue = "id") String sortField,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, direction, sortField);
        return userService.getSortedAndPagedData(pageable);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User with id : " + userId + "has deleted");

    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        UserInfoResponse response = userService.login(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, response.token()).body(response);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody RegisterRequest signupRequest, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            throw new RequestValidationException(errors.toString());
        }
        User user = userService.registerNewUser(signupRequest);
        return ResponseEntity.ok()
                .body(user);
    }


}
