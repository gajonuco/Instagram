package com.sistema_boft.service;



import com.sistema_boft.model.User;
import com.sistema_boft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final UserRepository userRepository;

    public User handleUserLogin(OAuth2AuthenticationToken authenticationToken) {
        OAuth2User oAuth2User = authenticationToken.getPrincipal();

        String instagramId = oAuth2User.getAttribute("id");
        String username = oAuth2User.getAttribute("username");
        String accessToken = authenticationToken.getAuthorizedClientRegistrationId(); // Para obter o token

        return userRepository.findByInstagramId(instagramId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .instagramId(instagramId)
                            .username(username)
                            .accessToken(accessToken)
                            .loggedOut(false)
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
