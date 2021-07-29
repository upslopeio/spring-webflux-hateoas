package io.upslope.hypermedia.v1;

import io.upslope.hypermedia.Issue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api/v1/issues")
public class IssuesControllerV1 {

    @GetMapping
    public Flux<Issue> listIssues() {
        return Flux.fromIterable(asList(new Issue("Get flux working")));
    }
}
