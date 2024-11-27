package com.sistema_boft.controller;



import com.sistema_boft.model.Photo;
import com.sistema_boft.model.PrintOrder;
import com.sistema_boft.model.User;
import com.sistema_boft.service.InstagramApiService;
import com.sistema_boft.service.PhotoService;
import com.sistema_boft.service.PrintOrderService;
import com.sistema_boft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InstagramPrintController {

    private final UserService userService;
    private final PhotoService photoService;
    private final PrintOrderService printOrderService;
    private final InstagramApiService instagramApiService;

    /**
     * Endpoint para autenticar o usuário via OAuth2.
     * O fluxo de OAuth2 será gerenciado automaticamente pelo Spring Security.
     * Após a autenticação, o usuário será redirecionado para uma URL específica.
     */
    @GetMapping("/auth/instagram")
    public ResponseEntity<String> authenticateUser() {
        // Spring Security OAuth2 Client será configurado para lidar com a autenticação.
        return ResponseEntity.ok("Redirecionando para o Instagram para autenticação...");
    }

    /**
     * Endpoint para buscar as fotos do usuário autenticado.
     *
     * @param userId ID do usuário autenticado.
     * @return Lista de fotos do usuário.
     */
    @GetMapping("/photos")
    public ResponseEntity<List<Photo>> getUserPhotos(@RequestParam Long userId) {
        User user = userService.findByInstagramId(userId.toString())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o usuário está logado e busca as fotos
        if (user.isLoggedOut()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Photo> photos = photoService.findPhotosByUser(user);

        if (photos.isEmpty()) {
            photos = instagramApiService.fetchUserPhotos(user);
            photoService.saveAll(photos);
        }

        return ResponseEntity.ok(photos);
    }

    /**
     * Endpoint para criar um pedido de impressão com fotos selecionadas.
     *
     * @param userId   ID do usuário autenticado.
     * @param photoIds IDs das fotos selecionadas.
     * @return Detalhes do pedido criado.
     */
    @PostMapping("/print")
    public ResponseEntity<PrintOrder> createPrintOrder(
            @RequestParam Long userId,
            @RequestBody List<Long> photoIds) {

        User user = userService.findByInstagramId(userId.toString())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.isLoggedOut()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Photo> selectedPhotos = photoService.findPhotosByUser(user).stream()
                .filter(photo -> photoIds.contains(photo.getId()))
                .toList();

        if (selectedPhotos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        PrintOrder printOrder = printOrderService.createOrder(user, selectedPhotos);

        return ResponseEntity.ok(printOrder);
    }

    /**
     * Endpoint para completar o pedido de impressão.
     *
     * @param orderId ID do pedido.
     * @return Mensagem de conclusão.
     */
    @PostMapping("/print/{orderId}/complete")
    public ResponseEntity<String> completePrintOrder(@PathVariable Long orderId) {
        PrintOrder order = printOrderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        printOrderService.completeOrder(order);

        return ResponseEntity.ok("Impressão concluída. Obrigado!");
    }

    /**
     * Endpoint para deslogar o usuário e limpar os dados.
     *
     * @param userId ID do usuário autenticado.
     * @return Mensagem de logout.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam Long userId) {
        User user = userService.findByInstagramId(userId.toString())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userService.logoutUser(user);
        photoService.deletePhotosByUser(user);

        return ResponseEntity.ok("Usuário deslogado e dados limpos.");
    }
}
