package hyfz.hyfz.PlayLoad.Response;

import hyfz.hyfz.user.User;

public record UserInfoResponse(
        String token,
        User user
) {
}
