# Trello API Automation Project

Bu proje, Trello API'sini kullanarak otomatik test senaryoları çalıştırmak için geliştirilmiş bir Java Maven projesidir. Rest-Assured kütüphanesi kullanılarak Page Object Pattern ve OOP prensiplerine uygun olarak yazılmıştır.

## Proje Özellikleri

- **Java 11** programlama dili
- **Maven** proje yapısı
- **Rest-Assured** API test kütüphanesi
- **Page Object Pattern** tasarım deseni
- **OOP (Object Oriented Programming)** prensipleri
- **JUnit 5** ve **TestNG** test framework'leri
- **Jackson** JSON işleme kütüphanesi

## Test Senaryosu

Proje aşağıdaki adımları otomatik olarak gerçekleştirir:

1. ✅ Trello üzerinde bir board oluşturur
2. ✅ Oluşturulan board'a iki tane kart oluşturur
3. ✅ Oluşturulan kartlardan rastgele bir tanesini günceller
4. ✅ Oluşturulan kartları siler
5. ✅ Oluşturulan board'u siler

## Kurulum

### Gereksinimler

- Java 11 veya üzeri
- Maven 3.6 veya üzeri
- Trello hesabı ve API erişimi

### API Anahtarları Alma

1. **API Key Alma:**
   - https://trello.com/app-key adresine gidin
   - Trello hesabınızla giriş yapın
   - API Key'inizi kopyalayın

2. **Token Alma:**
   - API Key'inizi aldıktan sonra, aşağıdaki URL'yi kullanarak token alın:
   ```
   https://trello.com/1/authorize?key=YOUR_API_KEY&response_type=token&scope=read,write&expiration=never&name=TrelloAPI
   ```
   - `YOUR_API_KEY` kısmını aldığınız API key ile değiştirin
   - Token'ınızı kopyalayın

### Proje Kurulumu

1. **Projeyi klonlayın:**
   ```bash
   git clone <repository-url>
   cd APIOtomasyonu
   ```

2. **API anahtarlarını yapılandırın:**
   - `src/test/resources/api.properties` dosyasını açın
   - `YOUR_API_KEY_HERE` kısmını API key'inizle değiştirin
   - `YOUR_API_TOKEN_HERE` kısmını token'ınızla değiştirin

   ```properties
   trello.api.key=your_actual_api_key_here
   trello.api.token=your_actual_token_here
   ```

3. **Projeyi derleyin:**
   ```bash
   mvn clean compile
   ```

## Test Çalıştırma

### JUnit 5 ile Çalıştırma

```bash
# Tüm testleri çalıştır
mvn test

# Sadece JUnit testlerini çalıştır
mvn test -Dtest=TrelloApiTest
```

### TestNG ile Çalıştırma

```bash
# TestNG testlerini çalıştır
mvn test -Dtest=TrelloApiTestNGTest
```

### IDE'de Çalıştırma

- IntelliJ IDEA veya Eclipse'de projeyi açın
- Test sınıflarını çalıştırın:
  - `TrelloApiTest.java` (JUnit 5)
  - `TrelloApiTestNGTest.java` (TestNG)

## Proje Yapısı

```
src/
├── main/java/com/trello/api/
│   ├── config/
│   │   ├── Config.java              # API konfigürasyon sabitleri
│   │   └── ApiCredentials.java      # API anahtarları yönetimi
│   ├── models/
│   │   ├── Board.java               # Board model sınıfı
│   │   ├── Card.java                # Card model sınıfı
│   │   └── List.java                # List model sınıfı
│   ├── pages/
│   │   ├── BasePage.java            # Temel sayfa sınıfı
│   │   ├── BoardPage.java           # Board API işlemleri
│   │   └── CardPage.java            # Card API işlemleri
│   └── utils/
│       └── RandomUtils.java         # Rastgele işlemler için yardımcı sınıf
└── test/
    ├── java/com/trello/api/tests/
    │   ├── TrelloApiTest.java       # JUnit 5 test sınıfı
    │   └── TrelloApiTestNGTest.java # TestNG test sınıfı
    └── resources/
        └── api.properties           # API anahtarları konfigürasyonu
```

## Özellikler

### Page Object Pattern
- Her API endpoint için ayrı sayfa sınıfları
- Ortak işlevler için `BasePage` sınıfı
- Temiz ve sürdürülebilir kod yapısı

### OOP Prensipleri
- Encapsulation: Model sınıflarında private alanlar ve getter/setter metodları
- Inheritance: `BasePage` sınıfından türetilen sayfa sınıfları
- Polymorphism: Farklı parametrelerle aşırı yüklenmiş metodlar
- Abstraction: Soyut sınıflar ve arayüzler

### Hata Yönetimi
- API yanıt kodları kontrolü
- Credential doğrulama
- Detaylı hata mesajları

### Loglama
- Her API çağrısı için detaylı loglar
- Test adımları için açıklayıcı çıktılar
- Hata durumlarında debug bilgileri

## API Endpoints Kullanılan

- `POST /boards` - Board oluşturma
- `GET /boards/{id}` - Board detaylarını alma
- `DELETE /boards/{id}` - Board silme
- `GET /boards/{id}/lists` - Board listelerini alma
- `POST /cards` - Kart oluşturma
- `GET /cards/{id}` - Kart detaylarını alma
- `PUT /cards/{id}` - Kart güncelleme
- `DELETE /cards/{id}` - Kart silme

## Sorun Giderme

### Yaygın Hatalar

1. **"API credentials are not loaded" hatası:**
   - `api.properties` dosyasının doğru konumda olduğundan emin olun
   - API key ve token'ın doğru girildiğini kontrol edin

2. **"401 Unauthorized" hatası:**
   - API key ve token'ın geçerli olduğundan emin olun
   - Token'ın doğru scope'lara sahip olduğunu kontrol edin

3. **"404 Not Found" hatası:**
   - Board veya kart ID'lerinin doğru olduğundan emin olun
   - Önceki test adımlarının başarılı olduğunu kontrol edin

### Debug Modu

Test çalıştırırken detaylı logları görmek için:

```bash
mvn test -Dlogging.level.com.trello.api=DEBUG
```

## Katkıda Bulunma

1. Projeyi fork edin
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Branch'inizi push edin (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun