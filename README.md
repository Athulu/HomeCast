# HomeCast
Aplikacja strumieniująca filmy z komputera na telewizor za pomocą aplikacji na androida z wbudowanym pilotem.

### Wymagania
1. Telewizor z podłączonym Chromecastem (w przypadku jeśli telewizor działa na systemie Google TV, to Chromecast nie jest wymagany).
2. Smartphone, telewizor i komputer z uruchomionym [HomeCast Server](https://github.com/Athulu/HomeCast-file-server-2.0) muszą być podłączone do tej samej sieci.

### Zrzuty ekranu aplikacji
<div align="center" style="display: flex; gap: 50px; flex-wrap: wrap;">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/a01c1ecb-fbe8-4efd-a1d5-441d079d96e4" width="200" alt="Obrazek 1">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/7e79765c-0bc9-42dd-afd4-aa45c2085581" width="200" alt="Obrazek 2">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/26fad53a-27ad-4401-a3b9-ff687acf741d" width="200" alt="Obrazek 3">
</div>
<div align="center" style="display: flex; gap: 50px; flex-wrap: wrap;">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/ceb9bd66-224a-434d-8ab4-fff13b79109b" width="200" alt="Obrazek 4">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/2630ecb6-d6dc-43b9-9f4d-651ee39f9df8" width="200" alt="Obrazek 5">
  <img src="https://github.com/Athulu/HomeCast/assets/56313840/7b815fcf-5103-4ac1-8082-60f2049cb483" width="200" alt="Obrazek 6">
</div>

### O aplikaacji
Włączając filmy z komputera na telewizorze byłem za każdym razem zmuszony do jednej z tych irytujących opcji:
1. Strumieniować bezpośrednio na telewizor, ale za każdym razem, gdy było trzeba zatrzymać/zmienić film/przewinąć to byłem zmuszony iść do mojego pokoju.
2. Przenosić laptopa do salonu razem z zasilaniem, aby podłączyć pod HDMI. Dalej byłem zmuszony wstawać z kanapy gdy chciałem zatrzymać lub zmienić film. Mniej chodzenia, ale więcej noszenia.

Potrzebowałem aplikacji podobnej do tych, które oferują popularne platformy streamingowe, ale która będzie korzystać z lokalnej bazy filmów.
Dość sporo korzystałem w domu z Chromecasta, więc zacząłem szukać czy nie mógłbym go w jakimś stopniu wykorzystać.
Wylądowałem na stronie dokumentacji Google Cast gdzie znalazłem ćwiczenia wprowadzające do Cast API, których [kod](https://developers.google.com/cast/codelabs/cast-videos-android) wykorzystałem jako projekt początkowy do rozpoczęcia pracy nad aplikacją.
