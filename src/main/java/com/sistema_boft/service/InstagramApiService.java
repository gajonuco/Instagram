

package com.sistema_boft.service;

import com.sistema_boft.model.Photo;
import com.sistema_boft.model.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstagramApiService {

    private final RestTemplate restTemplate; // Para chamadas HTTP

    public List<Photo> fetchUserPhotos(User user) {
        String accessToken = user.getAccessToken();
        String url = "https://graph.instagram.com/me/media?fields=id,caption,media_url&access_token=" + accessToken;

        // Chamada à API
        InstagramMediaResponse response = restTemplate.getForObject(url, InstagramMediaResponse.class);

        // Converter os dados retornados para uma lista de Photo
        List<Photo> photos = new ArrayList<>();
        if (response != null && response.getData() != null) {
            response.getData().forEach(media -> {
                if ("IMAGE".equals(media.getMediaType())) {
                    photos.add(Photo.builder()
                            .instagramPhotoId(media.getId())
                            .url(media.getMediaUrl())
                            .caption(media.getCaption())
                            .build());
                }
            });
        }

        return photos;
    }

    // DTO interno para mapear a resposta da API
    @Data
    public static class InstagramMediaResponse {
        private List<InstagramMedia> data;
    }

    @Data
    public static class InstagramMedia {
        private String id;
        private String caption;
        private String mediaType;
        private String mediaUrl;
    }
}
