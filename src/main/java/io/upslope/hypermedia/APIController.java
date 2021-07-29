package io.upslope.hypermedia;

import io.upslope.hypermedia.RootModel;
import io.upslope.hypermedia.issues.IssuesController;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class APIController {

    @GetMapping("")
    public Mono<RootModel> all() {
        List<WebFluxLinkBuilder.WebFluxLink> topLevelLinks = asList(
                linkTo(methodOn(IssuesController.class).all()).withRel("employees"),
                linkTo(methodOn(IssuesController.class).all()).withRel("issues")
        );

        return Flux.fromIterable(topLevelLinks)
                .flatMap(webFluxLink -> webFluxLink.toMono().map(Function.identity()))
                .collectList()
                .flatMap(links -> Mono.just(new RootModel().add(links)));
    }

}
