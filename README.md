#  aula_nmais1 - Evitando o problema N+1 com JPA

Este projeto foi desenvolvido durante a aula do canal [DevSuperior](https://www.youtube.com/@DevSuperior) ministrada pelo professor **Nelio Alves**. O foco foi entender e resolver o **problema N+1** no JPA usando boas práticas como **`JOIN FETCH`**, além de técnicas de paginação com `LIMIT` e consultas otimizadas com `IN`.

Aula: [🔗 Veja no YouTube](https://www.youtube.com/watch?v=sqbqoR-lMf8)

---

## O que foi aprendido

- O que é o problema N+1 em consultas JPA/Hibernate
- Como o `JOIN FETCH` pode evitar consultas adicionais desnecessárias
- Estratégias para paginação eficiente
- Consulta de associações `@ManyToMany` sem sobrecarregar o banco
- Uso do `IN` para buscar entidades associadas de forma performática

---

## 🛠️ Tecnologias utilizadas

- Java com Spring Boot
- Spring Data JPA
- H2 Database (banco em memória)
- Maven
- IDE: IntelliJ / VS Code

---

##  Exemplos de SQL utilizados

```sql
-- Paginação simples
SELECT * FROM tb_product LIMIT 0,5;
SELECT * FROM tb_product LIMIT 5,5;

-- INNER JOIN entre produtos e categorias (tabela muitos-para-muitos)
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id;

-- INNER JOIN com paginação (não recomendado em consultas complexas)
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id
LIMIT 0,5;

-- Consulta com IN (boa prática para evitar problema N+1)
SELECT * FROM tb_product WHERE id IN (1,2,3,4,5);

-- Consulta com JOIN + IN
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id
WHERE tb_product.id IN (1,2,3,4,5);
