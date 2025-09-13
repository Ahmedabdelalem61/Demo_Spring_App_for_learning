package scalable.com.example.demo.PostQuote;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scalable.com.example.demo.user.User;
import scalable.com.example.demo.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;


    public Quote createQuote(Long ownerId, String text) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + ownerId));
        Quote q = Quote.builder()
                .text(text)
                .owner(owner)
                .build();
        return quoteRepository.save(q);
    }

//    public Quote updateQuote(Long quoteId, Long requesterId, String newText) {
//        Quote q = quoteRepository.findById(quoteId)
//                .orElseThrow(() -> new IllegalArgumentException("Quote not found: " + quoteId));
//        if (!q.getOwner().getId().equals(requesterId)) {
//            throw new SecurityException("Only owner can update the quote");
//        }
//        q.setText(newText);
//        return quoteRepository.save(q);
//    }
//
//    public void deleteQuote(Long quoteId, Long requesterId) {
//        Quote q = quoteRepository.findById(quoteId)
//                .orElseThrow(() -> new IllegalArgumentException("Quote not found: " + quoteId));
//        if (!q.getOwner().getId().equals(requesterId)) {
//            throw new SecurityException("Only owner can delete the quote");
//        }
//        quoteRepository.delete(q);
//    }
//
//    public Optional<Quote> getQuote(Long id) {
//        return quoteRepository.findById(id);
//    }
//
//    public List<Quote> listAllQuotes() {
//        return quoteRepository.findAll();
//    }
//
//    public List<Quote> listByUser(Long userId) {
//        return quoteRepository.findAllByOwnerId(userId);
//    }
//
//    /**
//     * Toggle "love" from user for a quote:
//     * - if user already loved -> remove (unlove)
//     * - otherwise add love
//     */
//    @Transactional
//    public boolean toggleLove(Long quoteId, Long userId) {
//        Quote q = quoteRepository.findById(quoteId)
//                .orElseThrow(() -> new IllegalArgumentException("Quote not found: " + quoteId));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//
//        boolean nowLoved;
//        if (q.getLovedBy().contains(user)) {
//            q.getLovedBy().remove(user);
//            nowLoved = false;
//        } else {
//            q.getLovedBy().add(user);
//            nowLoved = true;
//        }
//        // quote is managed in transactional context so changes persist
//        return nowLoved;
//    }
//
//    public boolean hasUserLoved(Long quoteId, Long userId) {
//        Quote q = quoteRepository.findById(quoteId)
//                .orElseThrow(() -> new IllegalArgumentException("Quote not found: " + quoteId));
//        return q.getLovedBy().stream().anyMatch(u -> u.getId().equals(userId));
//    }
//
//    public int getLoveCount(Long quoteId) {
//        Quote q = quoteRepository.findById(quoteId)
//                .orElseThrow(() -> new IllegalArgumentException("Quote not found: " + quoteId));
//        return q.getLoveCount();
//    }
}
