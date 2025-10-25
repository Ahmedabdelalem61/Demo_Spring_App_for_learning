package scalable.com.example.demo.Quote;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    // Create quote
    // If you use Spring Security, you can use Principal to get the current user and map to userId.
    // Here we accept ownerId in body param for simplicity; change to Principal-based lookups if you have auth.
    @PostMapping
    public ResponseEntity<QuoteResponse> createQuote(@RequestParam Long ownerId,
                                                     @RequestBody QuoteRequest request) {
        Quote q = quoteService.createQuote(ownerId, request.getText());
        return ResponseEntity.ok(toResponse(q, ownerId));
    }

    // Update quote
    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponse> updateQuote(@PathVariable Long id,
                                                     @RequestParam Long requesterId,
                                                     @RequestBody QuoteRequest request) {
        Quote q = quoteService.updateQuote(id, requesterId, request.getText());
        return ResponseEntity.ok(toResponse(q, requesterId));
    }

    // Delete quote
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id,
                                            @RequestParam Long requesterId) {
        quoteService.deleteQuote(id, requesterId);
        return ResponseEntity.noContent().build();
    }

    // Get single quote
    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable Long id,
                                                  @RequestParam(required = false) Long currentUserId) {
        Quote q = quoteService.getQuote(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        return ResponseEntity.ok(toResponse(q, currentUserId));
    }

    // List all
    @GetMapping
    public ResponseEntity<List<QuoteResponse>> listAll(@RequestParam(required = false) Long currentUserId) {
        List<QuoteResponse> list = quoteService.listAllQuotes().stream()
                .map(q -> toResponse(q, currentUserId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Toggle love. Returns the new state (true if now loved)
    @PostMapping("/{id}/love")
    public ResponseEntity<?> toggleLove(@PathVariable Long id, @RequestParam Long userId) {
        boolean nowLoved = quoteService.toggleLove(id, userId);
        return ResponseEntity.ok().body(java.util.Map.of("quoteId", id, "loved", nowLoved, "loveCount", quoteService.getLoveCount(id)));
    }

    // Get love count
    @GetMapping("/{id}/loves")
    public ResponseEntity<?> getLoveCount(@PathVariable Long id) {
        int count = quoteService.getLoveCount(id);
        return ResponseEntity.ok(java.util.Map.of("quoteId", id, "loveCount", count));
    }

    // Check if a user loved the quote
    @GetMapping("/{id}/loved")
    public ResponseEntity<?> hasUserLoved(@PathVariable Long id, @RequestParam Long userId) {
        boolean loved = quoteService.hasUserLoved(id, userId);
        return ResponseEntity.ok(java.util.Map.of("quoteId", id, "userId", userId, "loved", loved));
    }
//
    // mapping helper
    private QuoteResponse toResponse(Quote q, Long currentUserId) {
        Long ownerId = q.getOwner() == null ? null : q.getOwner().getId();
        boolean lovedByCurrentUser = currentUserId != null && q.getLovedBy().stream().anyMatch(u -> u.getId().equals(currentUserId));
        return QuoteResponse.builder()
                .id(q.getId())
                .text(q.getText())
                .ownerId(ownerId)
                .ownerName(q.getOwner() != null ? q.getOwner().getUsername() : null) // adjust getter if needed
                .loveCount(q.getLoveCount())
                .lovedByCurrentUser(lovedByCurrentUser)
                .build();
    }
}
