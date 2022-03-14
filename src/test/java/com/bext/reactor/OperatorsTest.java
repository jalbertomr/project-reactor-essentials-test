package com.bext.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class OperatorsTest {

    @Test
    public void subscribeOnSchedulersSingleTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 4)
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void subscribeOnSchedulersBoundedElasticTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 4)
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4)
                .verifyComplete();
    }

    @Test
    public void publishOnSchedulersSingleTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 6)
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6)
                .verifyComplete();
    }

    @Test
    public void publishOnSchedulersBoundedElasticTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 6)
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6)
                .verifyComplete();
    }

    @Test
    public void multiplePublishOnSchedulersTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 8)
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("4th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8)
                .verifyComplete();
    }

    @Test
    public void multipleSubscribeOnSchedulersTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 8)
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("4th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8)
                .verifyComplete();
    }

    @Test
    public void multiplePublishOnSubscribeOnSchedulersTest() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 8)
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("4th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8)
                .verifyComplete();
    }

    @Test
    public void multiplePublishOnSubscribeOnSchedulers2Test() {
        Flux<Integer> fluxSubscribeOn = Flux.range(1, 8)
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("1st map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("2nd map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(i -> {
                    log.info("3th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("4th map - {} - on thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(fluxSubscribeOn)
                .expectSubscription()
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8)
                .verifyComplete();
    }

    @Test
    public void subscribeOnIOTest() {
        Mono<List<String>> listMono = Mono.fromCallable(() -> Files.readAllLines(Path.of("data.txt")))
                .log()
                .subscribeOn(Schedulers.boundedElastic());

        StepVerifier.create(listMono)
                .expectSubscription()
                .thenConsumeWhile(lines -> {
                    Assertions.assertFalse(lines.isEmpty());
                    log.info("lines size: {}", lines.size());
                    return true;
                })
                .verifyComplete();
    }

}
