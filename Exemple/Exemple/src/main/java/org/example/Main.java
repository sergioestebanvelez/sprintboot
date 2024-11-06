package org.example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
// Crear una lista de transacciones y usuarios para los ejemplos
        Flux<Transaction> transactions = Flux.just(new Transaction(100), new Transaction(200), new Transaction(300));
        Flux<User> users = Flux.just(new User("Alice"), new User("Bob"));

        // Ejemplo de uso de 'map' - duplicar los puntos de recompensa
        System.out.println("Ejemplo de map:");
        Flux<Integer> rewards = transactions.map(tx -> tx.getAmount() * 2);
        rewards.subscribe(System.out::println); // Outputs: 200, 400, 600

        // Ejemplo de uso de 'filter' - filtrar transacciones mayores a $100
        System.out.println("\nEjemplo de filter:");
        Flux<Transaction> largeTransactions = transactions.filter(tx -> tx.getAmount() > 100);
        largeTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 200, 300



        // Ejemplo de uso de 'zip' - combinar transacciones con usuarios
        System.out.println("\nEjemplo de zip:");
        Flux<String> transactionUserDetails = Flux.zip(transactions, users,
                (tx, user) -> user.getName() + " hizo una transacción de " + tx.getAmount());
        transactionUserDetails.subscribe(System.out::println);

        // Ejemplo de uso de 'merge' - mezclar transacciones de dos cuentas
        System.out.println("\nEjemplo de merge:");
        Flux<Transaction> account1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> account2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> allTransactions = Flux.merge(account1Transactions, account2Transactions);
        allTransactions.subscribe(tx -> System.out.println(tx.getAmount())); // Outputs: 100, 200, 300, 400

        // Ejemplo de uso de 'collectList' - recolectar todas las transacciones
        System.out.println("\nEjemplo de collectList:");
        Mono<List<Transaction>> transactionList = transactions.collectList();
        transactionList.subscribe(list -> System.out.println("Recolección de " + list.size() + " transacciones")); // Outputs: 3

        // Ejemplo de uso de 'reduce' - calcular el total de todas las transacciones
        System.out.println("\nEjemplo de reduce:");
        Mono<Integer> totalAmount = transactions.map(Transaction::getAmount).reduce(0, Integer::sum);
        totalAmount.subscribe(System.out::println); // Outputs: 600

        // Ejemplo de uso de 'mergeWith' - mezclar notificaciones de transacciones de dos cuentas
        System.out.println("\nEjemplo de mergeWith:");
        Flux<String> account1Notifications = Flux.just("Tx1: $100", "Tx2: $200");
        Flux<String> account2Notifications = Flux.just("Tx3: $300", "Tx4: $400");
        Flux<String> allNotifications = account1Notifications.mergeWith(account2Notifications);
        allNotifications.subscribe(System.out::println);

        // Ejemplo de uso de 'concatWith' - concatenar transacciones de un día con las del día siguiente
        System.out.println("\nEjemplo de concatWith:");
        Flux<Transaction> day1Transactions = Flux.just(new Transaction(100), new Transaction(200));
        Flux<Transaction> day2Transactions = Flux.just(new Transaction(300), new Transaction(400));
        Flux<Transaction> concatenatedTransactions = day1Transactions.concatWith(day2Transactions);
        concatenatedTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        // Ejemplo de uso de 'switchIfEmpty' - mensaje por defecto si no hay transacciones
        System.out.println("\nEjemplo de switchIfEmpty:");
        Flux<Transaction> emptyTransactions = Flux.empty();
        Flux<Transaction> transactionsWithDefault = emptyTransactions.switchIfEmpty(Flux.just(new Transaction(0)));
        transactionsWithDefault.subscribe(tx -> System.out.println(tx.getAmount()));

        // Ejemplo de uso de 'take' - tomar las primeras 2 transacciones del día
        System.out.println("\nEjemplo de take:");
        Flux<Transaction> firstTwoTransactions = transactions.take(2);
        firstTwoTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        // Ejemplo de uso de 'takeLast' - tomar las últimas 2 transacciones
        System.out.println("\nEjemplo de takeLast:");
        Flux<Transaction> lastTwoTransactions = transactions.takeLast(2);
        lastTwoTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        // Ejemplo de uso de 'skip' - omitir las primeras transacciones
        System.out.println("\nEjemplo de skip:");
        Flux<Transaction> remainingTransactions = transactions.skip(1);
        remainingTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        // Ejemplo de uso de 'skipLast' - omitir las últimas transacciones
        System.out.println("\nEjemplo de skipLast:");
        Flux<Transaction> initialTransactions = transactions.skipLast(1);
        initialTransactions.subscribe(tx -> System.out.println(tx.getAmount()));

        
        }
    }
