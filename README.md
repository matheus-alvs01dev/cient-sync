# ClientSync

Aplicativo Android de sincronização de clientes via API REST, desenvolvido como projeto acadêmico.

---

## Tecnologias e Versões

| Tecnologia | Versão |
|---|---|
| **Android SDK** | compileSdk 36 (API 36, extension level 1), minSdk 24 (Android 7.0), targetSdk 36 |
| **Kotlin** | 2.2.10 |
| **AGP (Android Gradle Plugin)** | 9.2.1 |
| **Gradle** | 9.4.1 |
| **Java** | 11 |
| **AndroidX Core KTX** | 1.10.1 |
| **AndroidX Lifecycle Runtime KTX** | 2.6.1 |
| **Compose BOM** | 2026.02.01 |
| **JUnit** | 4.13.2 |
| **Espresso** | 3.5.1 |

> Nenhuma biblioteca externa (Retrofit, Room, Gson, etc.) é utilizada. Todo o código usa apenas o SDK nativo do Android.

---

## Estrutura do Projeto

```
ClientSync/
├── app/
│   ├── build.gradle.kts          # Configurações do módulo app
│   └── src/main/
│       ├── AndroidManifest.xml    # Permissões e declaração de Activities
│       ├── java/com/example/clientsync/
│       │   ├── MainActivity.kt    # Tela principal + consumo da API
│       │   ├── ListaActivity.kt   # Tela de listagem dos clientes salvos
│       │   └── DatabaseHelper.kt  # Gerenciador do banco SQLite
│       └── res/
│           └── layout/
│               ├── activity_main.xml   # Layout com botão "Sincronizar Clientes"
│               └── activity_lista.xml  # Layout com ListView
├── build.gradle.kts              # Configuração raiz do Gradle
├── gradle/
│   ├── libs.versions.toml        # Catálogo de versões
│   └── wrapper/                  # Gradle wrapper
└── settings.gradle.kts
```

---

## Funcionalidades

1. **Sincronizar clientes via API** — Botão na tela principal faz uma requisição HTTP GET para a API e salva os clientes no banco local.
2. **Listagem de clientes** — Após a sincronização, o app navega automaticamente para uma tela que exibe todos os clientes salvos em uma `ListView`.

### API consumida

```
GET http://hwsistemas.homelinux.com/apiclienteteste/api/cliente/retornaclientes?tipo=json
```

Formato da resposta:
```json
[
  {"$id":"7","codigo":7,"nome":"cliente 7","cpf":"012345678917"},
  {"$id":"8","codigo":8,"nome":"cliente 8","cpf":"123456789018"}
]
```

### Banco de dados local (SQLite)

O arquivo `clientsync.db` é criado automaticamente em runtime pelo `SQLiteOpenHelper` no primeiro acesso (gravação ou leitura). Ele fica armazenado em:

```
/data/data/com.example.clientsync/databases/clientsync.db
```

Tabela `clientes`:

| Coluna | Tipo | Descrição |
|---|---|---|
| `id` | INTEGER | Chave primária |
| `nome` | TEXT | Nome do cliente |
| `cpf` | TEXT | CPF do cliente |
| `created_at` | TEXT | Data de criação (reservado) |
| `updated_at` | TEXT | Data de atualização (reservado) |

---

## Como Rodar

### Pré-requisitos

- **Android Studio** (Ladybug ou superior recomendado)
- **JDK 11+**
- Dispositivo Android 7.0+ ou emulador com API 24+

### Passos

1. **Clone o projeto** (ou abra a pasta no Android Studio)
   ```bash
   git clone <url-do-repositorio>
   ```

2. **Abra no Android Studio**
   - File → Open → selecione a pasta `ClientSync/`

3. **Aguarde a sincronização do Gradle**
   - O Android Studio baixará automaticamente as dependências e o Gradle wrapper.

4. **Execute o app**
   - Conecte um dispositivo Android via USB (com depuração USB ativada) ou inicie um emulador.
   - Clique em **Run** (ícone verde ▶) ou pressione `Shift + F10`.

5. **Usar o app**
   - Na tela inicial, toque em **"Sincronizar Clientes"**.
   - O app fará o download dos clientes da API e abrirá automaticamente a lista.

### Via linha de comando (alternativa)

```bash
# Verificar dispositivos conectados
./gradlew devices

# Build e instalação no dispositivo/emulador
./gradlew installDebug

# Build APK (sem instalar)
./gradlew assembleDebug
# APK gerado em: app/build/outputs/apk/debug/app-debug.apk
```
