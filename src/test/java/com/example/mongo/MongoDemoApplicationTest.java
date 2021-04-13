package com.example.mongo;

import com.example.mongo.model.ZipCodes;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Stack;

class MongoDemoApplicationTest {

    public static void main(String[] args) throws InterruptedException {
        Disposable subscribe = Flux.just("one", "four", "three", "eleven")
                .delayElements(Duration.ofMillis(2000))
                .subscribe(e -> System.out.println("subscribe " + e + " - " + Thread.currentThread().getName()));
        Thread.sleep(9000);
    }

    @Test
    public void testFlux() throws InterruptedException {
        Disposable subscribe = Flux.just(Arrays.asList("one", "four", "three", "eleven"))
                .delayElements(Duration.ofMillis(500))
                .flatMap(Flux::fromIterable)
                .subscribe(e -> System.out.println("final subscribe " + e + " - " + Thread.currentThread().getName()));
        Thread.sleep(2500);
        subscribe.dispose();
    }

    @Test
    public void testFlux2() throws InterruptedException {
        Disposable subscribe = Flux.fromIterable(Arrays.asList("one", "four", "three", "eleven"))
                .subscribeOn(Schedulers.boundedElastic())
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println)
                .subscribe(e -> System.out.println("final subscribe " + e + " - " + Thread.currentThread().getName()));
        Thread.sleep(2500);
        subscribe.dispose();
    }

    @Test
    public void webClient() throws InterruptedException {
        WebClient.create("http://localhost:8080")
                .get().uri("/zips/{state}", "MA")
                .retrieve()
                .bodyToFlux(ZipCodes.class)
//                .publish(10)
                .log()
                .subscribe(e -> System.out.println("Zips fetched " + e + " - " + Thread.currentThread().getName()));
        Thread.sleep(10000);
    }

    @Test
    public void reverse() {
        String name = "Hello java";
        char [] c = name.toCharArray();
        StringBuilder rev = new StringBuilder();
        for (int i = c.length-1; i >=0; i--) {
            rev.append(c[i]);
        }
        System.out.println(rev.toString());
    }

    @Test
    public void balance() {
        String braces = "{{}}{}{}{}{}{{}}}";

        int i = 0;
        for(char c : braces.toCharArray()) {
            i = c == '{' ? ++i : --i;
        }
        System.out.println(i);
    }

    @Test
    public void balanceDifferent() {
//        String braces = "{{}}";
        String braces = "{{}}{[]}[{}]{}{}{{}}";
        boolean balanced = true;

        Stack<Character> stack = new Stack<>();
        for (char c : braces.toCharArray()) {
            if(c == '{' || c == '[') {
//                System.out.println("pushing " + c);
                stack.push(c);
                continue;
            }

            if(stack.isEmpty()) {
                balanced = false;
                break;
            }

            switch (c) {
                case '}' :
                    char e1 = stack.pop();
//                    System.out.println("popped " + e1);
                    if(e1 != '{') {
                        balanced = false;
                    }
                    break;
                case ']' :
                    char e2 = stack.pop();
//                    System.out.println("popped " + e2);
                    if(e2 != '[') {
                        balanced = false;
                    }
                    break;
            }
            if(!balanced) break;

        }
        System.out.println(balanced);
    }

    @Test
    public void shift() {
        String s = "chaitu";
        s = s.substring(s.length()-2) + s.substring(0, s.length()-2);
        System.out.println(s);
    }

    @Test
    public void decode() {
        byte[] decode = Base64.getDecoder().decode("eyAgICAicG9saWN5IiA6IHsgICAgICAgICJuYW1lIiA6ICJTU09QYXNzd29yZCIsICAgICAgICAidmVyc2lvbiIgOiAiMS4wIiwgICAgICAgICJzcGVjaWZpY2F0aW9uIiA6ICIxLjAiLCAgICAgICAgImRlc2NyaXB0aW9uIiA6ICJDdXJyZW50IFBhc3N3b3JkIFBvbGljeSBmb3IgVVMgYW5kIG1vc3QgSW50ZXJuYXRpb25hbCBtYXJrZXRzIiAgICB9LCAgICAibWluaW11bUxlbmd0aCIgOiA4LCAgICAibWF4aW11bUxlbmd0aCIgOiAyMCwgICAgIm1heUNvbnRhaW4iIDogImFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVowMTIzNDU2Nzg5JSZfPyM9LSIsICAgICJtdXN0Q29udGFpbiIgOiBbICAgICAgICAgICAgICAgICAgICAgeyAiYXRMZWFzdCIgOiAxLCAib2ZTZXQiIDogImFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6QUJDREVGR0hJSktOTU5PUFFSU1RVVldYWVoiIH0sICAgICAgICAgICAgICAgICAgICAgeyAiYXRMZWFzdCIgOiAxLCAib2ZTZXQiIDogIjAxMjM0NTY3ODkiIH0gICAgICAgICAgICAgICAgICAgICBdLCAgICAiY2FzZVNlbnNpdGl2ZSIgOiBmYWxzZSwgICAgInNwYWNlc0lnbm9yZWQiIDogdHJ1ZSwgICAgIm11c3ROb3RCZVVzZXJuYW1lIiA6IHRydWUsICAgICJtdXN0Tm90QmVDdXJyZW50UGFzc3dvcmQiIDogdHJ1ZSwgICAgIm11c3ROb3RCZVByZXZpb3VzTlBhc3N3b3JkcyIgOiAxfQ==");
        System.out.println(new String(decode));
    }
}
