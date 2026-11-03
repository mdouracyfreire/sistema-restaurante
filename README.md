
# 🍔 Sistema de Restaurante

![Java](https://img.shields.io/badge/Java-23-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluído-2E8B57?style=for-the-badge)
![Interface](https://img.shields.io/badge/Interface-CLI-374151?style=for-the-badge)

> Sistema de gerenciamento de restaurante desenvolvido em Java, com foco na aplicação de Programação Orientada a Objetos, organização arquitetural, regras de negócio, controle financeiro e testes automatizados.

**Projeto 01 — Formação Java + IA | ElasTech**

## 📌 Sobre o projeto

A aplicação simula as principais operações de um restaurante, desde a criação e personalização de pedidos até o registro de pagamentos e o fechamento financeiro.

O sistema funciona por meio de uma **interface interativa no terminal (CLI)**, com menus organizados, validação de entradas e navegação entre telas.

Seu desenvolvimento prioriza a separação de responsabilidades, a reutilização de código e a implementação de regras de negócio consistentes.

## ⚙️ Funcionalidades

- **🍔 Cardápio:** disponibiliza diferentes tipos de lanches com preços definidos.
- **🧀 Personalização:** permite remover ingredientes e adicionar extras, como cheddar e bacon.
- **📝 Gerenciamento de pedidos:** possibilita criar, consultar, modificar e cancelar pedidos.
- **💳 Pagamentos:** registra pagamentos e gorjetas, impedindo pagamentos duplicados.
- **💰 Controle financeiro:** gerencia as transações realizadas durante o atendimento.
- **📊 Fechamento do dia:** apresenta vendas, gorjetas, valores recebidos e pendentes.
- **📈 Relatórios:** contabiliza diferentes combinações e tipos de lanches vendidos.
- **🖥️ Interface CLI:** oferece menus interativos, mensagens de feedback e limpeza automática do terminal.

### 🍽️ Cardápio

| Lanche | Preço |
|---|---:|
| X-Burger | R$ 16,00 |
| X-Salada | R$ 18,00 |
| X-Bacon | R$ 22,00 |

**Adicionais disponíveis:**

| Adicional | Preço |
|---|---:|
| Cheddar | R$ 4,00 |
| Bacon extra | R$ 6,00 |

O valor final de cada pedido é calculado automaticamente, considerando os lanches e adicionais selecionados.

## 🛠️ Tecnologias e conceitos

**Tecnologias:**
- Java 23
- Apache Maven
- JUnit 5
- Git e GitHub

**Conceitos aplicados:**
- Programação Orientada a Objetos;
- Encapsulamento, herança e polimorfismo;
- Padrões Factory, Decorator e Facade;
- Coleções Java (`List`, `Set` e `HashSet`);
- Sobrescrita de `equals()` e `hashCode()`;
- Tratamento de exceções;
- Testes automatizados;
- Separação de responsabilidades.

## 🎥 Demonstração do Projeto

Confira o vídeo de apresentação do **Sistema de Restaurante**, com uma explicação sobre sua estrutura, principais funcionalidades e demonstração prática da aplicação.

<div align="center">

<a href="https://youtu.be/C2bLHgniGEU">
  <img src="https://img.shields.io/badge/▶_Assistir_Demonstração-YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white" alt="Assistir demonstração do projeto"/>
</a>

<br>

*Apresentação técnica e demonstração de execução do sistema.*

</div>

## 🏗️ Estrutura do projeto

O código está organizado em pacotes com responsabilidades específicas:

```text
src/
├── main/java/
│   ├── app/          # Inicialização e coordenação
│   ├── ui/           # Interface e interação com usuário
│   ├── cardapio/     # Lanches e adicionais
│   ├── pedido/       # Gerenciamento de pedidos
│   └── financeiro/   # Pagamentos e controle do caixa
│
└── test/java/        # Testes automatizados
```

A classe `RestauranteFacade` centraliza o acesso às operações do sistema, enquanto as demais classes são responsáveis pelas funcionalidades de seus respectivos domínios.

Essa organização facilita a manutenção, a reutilização de código e a evolução da aplicação.

## 🚀 Como executar

### Pré-requisitos

- JDK 23;
- Apache Maven;
- Git (opcional, para clonar o repositório).

### 1. Clone o repositório

```bash
git clone https://github.com/mdouracyfreire/sistema-restaurante.git
```

### 2. Acessar o diretório

```bash
cd sistema-restaurante
```

### 3. Verificar o Java

```bash
java -version
```

A versão utilizada no projeto é o Java 23.

### 4. Verificar o Maven

```bash
mvn -version
```

O Maven deve reconhecer corretamente a instalação do Java.

### 5. Compilar o projeto

```bash
mvn clean compile
```

Esse comando limpa os arquivos de build anteriores e compila o código-fonte.

### 6. Executar os testes

```bash
mvn clean test
```

O Maven executará os testes automatizados configurados no projeto.

### 7. Gerar o pacote

```bash
mvn clean package
```

O comando compila o projeto, executa os testes e gera o artefato na pasta `target/`.

### 8. Executar a aplicação

Se o JAR gerado estiver configurado com a classe principal no manifesto, utilize:

```bash
java -jar target/sistema-restaurante-1.0-SNAPSHOT.jar
```

O nome do arquivo depende das configurações presentes no `pom.xml`.

Caso o JAR não esteja configurado como executável, é possível executar a classe principal a partir das classes compiladas:

```bash
java -cp target/classes app.Main
```


> **Observação:** recomenda-se utilizar o Git Bash ou outro terminal compatível com sequências ANSI para aproveitar os recursos visuais da interface.

## 🧪 Testes automatizados

O projeto utiliza **JUnit 5** para validar funcionalidades e regras de negócio, incluindo operações com pedidos, cálculos financeiros, cancelamentos e controle de transações.

### Cenários contemplados

Entre os comportamentos avaliados estão:

| Cenário | Finalidade |
|---|---|
| Operações básicas | Verificar a configuração inicial do ambiente de testes |
| Cálculo de valores | Conferir a soma dos itens e adicionais |
| Fechamento financeiro | Validar os valores consolidados |
| Cancelamento de pedidos | Verificar o comportamento da operação |
| Combinações vendidas | Validar o tratamento de combinações distintas |
| Pedidos pagos | Verificar as restrições associadas ao estado do pedido |
| Transações | Verificar a prevenção de registros duplicados |

A cobertura exata depende dos testes implementados em `src/test/java`.

### Executando a suíte

```bash
mvn test
```

### Executando uma compilação limpa com testes

```bash
mvn clean test
```

## 📚 Aprendizados

Durante o desenvolvimento deste projeto foram praticados conceitos importantes de desenvolvimento de software com Java, como:

- Programação Orientada a Objetos (POO)
- Encapsulamento, herança, abstração e polimorfismo
- Aplicação dos padrões de projeto Factory, Decorator e Facade
- Organização de classes e pacotes
- Separação de responsabilidades
- Utilização de coleções Java (`List`, `Set` e `HashSet`)
- Sobrescrita dos métodos `equals()` e `hashCode()`
- Tratamento de exceções e validação de dados
- Implementação de regras de negócio
- Gerenciamento de projetos com Maven
- Desenvolvimento de testes automatizados com JUnit 5
- Construção de interfaces interativas no terminal (CLI)

---

## 🏁 Licença

Este projeto foi desenvolvido para fins educacionais como atividade proposta na **Formação Java + IA | ElasTech**, promovida pela **SoulCode Academy em parceria com o PagBank**.

Sinta-se à vontade para estudar, adaptar e utilizar o código como referência para aprendizado.

---

## 👩‍💻 Autora

<div align="center">

Desenvolvido com 💛 por **Douracy Freire**

<a href="https://github.com/mdouracyfreire">
  <img src="https://img.shields.io/badge/GitHub-Douracy%20Freire-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
</a>

<a href="https://www.linkedin.com/in/douracy/">
  <img src="https://img.shields.io/badge/LinkedIn-Douracy%20Freire-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
</a>

</div>

---

<div align="center">

### 🍔 Sistema de Restaurante

**Java • Programação Orientada a Objetos • Maven • JUnit**

*Projeto acadêmico desenvolvido com foco em aprendizagem, boas práticas e evolução técnica.*

**Formação Java + IA | ElasTech — SoulCode Academy + PagBank**

</div>
