

package com.sistema_boft.service;

import com.sistema_boft.model.Photo;
import com.sistema_boft.model.User;
import com.sistema_boft.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public void saveAll(List<Photo> photos) {
        photoRepository.saveAll(photos);
    }

    public List<Photo> findPhotosByUser(User user) {
        return photoRepository.findByUser(user);
    }

    public void deletePhotosByUser(User user) {
        photoRepository.deleteByUser(user);
    }
}
