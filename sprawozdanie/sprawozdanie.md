---
title: Sprawozdanie - projekt z programowania w języku Java
author:
- Grzegorz Bujak
- Arkadiusz Markowski
- grupa 2ID11B
- Politechnika świętokrzyska w Kielcach
lang: pl
pagesize: a4
geometry: margin=2.5cm
toc: t
---

# Założenia projektu

Nasz projekt polegał na napisaniu edytora tekstu w języku Java z wykorzystaniem
biblioteki JavaFX. Edytor ma funkcję pisania tego samego dokumentu przez wiele
osób w tym samym czasie. Podczas pisania, synchronizuje dokument pomiędzy
użytkownikami.

Moduł sieciowy edytora używa topologii klient-serwer, ale serwer nie jest
aplikacją terminalową. Interfejs graficzny zapewnia możliwość dołączenia do
serwera oraz hostowania własnego dokumentu.

# Moduł sieciowy

Moduł sieciowy naszego projektu składa się z klas:

- `Networker<T extends Serializable>`

    Jedna instancja dla serwera i klienta. Jeśli program działa w trybie
    serwera, tworzy instancję `ServerSocket`, która ciągle akceptuje nowych
    klientów. W trybie klienta, tworzy `Dispatcher<T>`, który będzie wysyłał
    wiadomości tylko do serwera oraz `SocketHandler<T>`, który będzie odbierał
    wiadomości od serwera.

- `Dispatcher<T extends Serializable>`

    Klasa odpowiada za wysyłanie wiadomości.

    Przechowuje listę wszystkich wiadomości (obiekty klasy `T`) oraz
    `HashMap<Socket, Integer>`, który przechowuje sockety, do których ma wysyłać
    wiadomości oraz `Integer` będący ID ostatniej wiadomości wysłanej do
    socketa.
    
    Zapewnia medotę `void addAndDispatch(T)`, która dodaje wiadomość typu `T` do
    listy wiadomości i wysyła tą wiadomość do wszystkich socketów.

    Ma też metodę `void addSocket(Socket)`, która dodaje Socket do mapy i
    wywołuje metodę `dispatch()`, która wyśle temu socketowi wszystkie
    wiadomości dodane do Dispatchera od początky działania programu.

- `SocketHandler<T extends Serializable>`

    Klasa jest odpowiedzialna za odbieranie wiadomości. Do jej konstruktora
    należy podać domknięcie (obiekt interfejsu funkcjonalnego `Consumer<T>`).
    Obiekt w nieskończoność odbiera nadchodzące wiadomości i wywołuje domknięcie
    z każdą wiadomością.

**Wszystkie klasy powinny działać na własnych wątkach. Nie zapewniają możliwości
zamknięcia, więc wątki powinny być ustawione jako Daemony.**

# Edytor tekstu

Edytor tekstu to lista `ListView` bloków tekstowych.

Bloki tekstowe to obiekty naszej własnej klasy `ExpandingTextArea`. Jest to
`TextArea`, która zmienia swoją wysokość podczas pisania.

Wciśnięcie klawicza enter nie powoduje wstawienia nowej linii w polu tekstowym,
lecz jest przechwytywane przez klasę `MainGuiController`, która wstawia do listy
nowe pole tekstowe po tym, w którym znajduje się kursor oraz kopiuje do niego
wszystko co znajdowało się za kursorem w polu tekstowym.

Wciśnięcie backspace, gdy kursor znajduje się na początku pola tekstowego
powoduje skasowanie tego pola tekstowego i skopiowanie jego zawartości za
kursorem do poprzedniego pola tekstowego.
