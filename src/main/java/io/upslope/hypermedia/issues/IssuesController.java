package io.upslope.hypermedia.issues;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/issues")
@CrossOrigin(origins = "*")
public class IssuesController {

    private List<Issue> issues = asList(
            new Issue(UUID.fromString("039a486b-39df-496f-a32d-302cdb39fab7"), "get webflux working"),
            new Issue(UUID.fromString("514f6774-9edd-4796-80c0-97944288e99e"), "get show working"),
            new Issue(UUID.fromString("2bc56990-539b-46b9-b940-6a0fa8562322"), "get list working"),
            new Issue(UUID.fromString("751a1a2f-bb02-4741-a76b-e4759d8286f6"), "get templates working"),
            new Issue(UUID.fromString("defae8a5-a4b4-4826-b22a-4b59ea4d63f6"), "get presentation done")
    );

    @GetMapping("")
    public Mono<CollectionModel<EntityModel<Issue>>> all() {
        var controller = methodOn(IssuesController.class);

        List<WebFluxLinkBuilder.WebFluxLink> collectionLinks = asList(
                linkTo(controller.all()).withSelfRel(),
                linkTo(controller.show(null)).withRel("findOne")
        );

        Mono<List<Link>> listMono = Flux.fromIterable(collectionLinks)
                .flatMap(webFluxLink -> webFluxLink.toMono().map(Function.identity()))
                .collectList();

        return Flux.fromIterable(issues)
                .flatMap(issue -> linkTo(controller.show(issue.getId()))
                        .withSelfRel()
                        .toMono()
                        .map(link -> EntityModel.of(issue, link)))
                .collectList()
                .flatMap(resources -> listMono.flatMap(links -> Mono.just(CollectionModel.of(resources, links))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<EntityModel<Issue>>> show(@PathVariable("id") UUID id) {
        var controller = methodOn(IssuesController.class);

        Optional<Issue> optionalIssue = issues.stream().filter(x -> x.getId().equals(id)).findFirst();

        return optionalIssue
                .map(issue -> ResponseEntity
                        .status(200)
                        .body(linkTo(controller.show(id))
                                .withSelfRel()
                                .toMono()
                                .map(link -> EntityModel.of(issue, link))))
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body(Mono.empty()));
    }

}
