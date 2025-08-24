package scalable.com.example.demo.PostQuote;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/post-quote")
@RequiredArgsConstructor
public class PostQuoteController {

    @GetMapping
    public ResponseEntity<String> postQuote(){
        return ResponseEntity.ok("Na3eem elga7l are Calling you htklsn?");
    }

}
