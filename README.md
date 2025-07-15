#  Projeto aula_nmais1 — Evitando o problema N+1 no JPA

Esse projeto foi feito durante a aula do canal [DevSuperior](https://www.youtube.com/@DevSuperior), com o professor **Nelio Alves**. O tema foi um dos problemas mais comuns pra quem usa JPA: o famoso **N+1**, que acontece quando o framework faz uma consulta no banco pra cada item retornado (o que pode virar um caos em performance 😬).

Com a aula, aprendi a usar o `JOIN FETCH` da forma certa, fazer paginação sem causar problema e também melhorar as consultas usando `IN` quando necessário.

📺 Aula completa: [YouTube - DevSuperior](https://www.youtube.com/watch?v=sqbqoR-lMf8)

---

##  O que eu aprendi

- O que é o problema N+1 na prática
- Como evitar esse problema com `JOIN FETCH`
- Cuidados ao usar paginação junto com relacionamentos
- Melhor forma de fazer consultas `@ManyToMany` com performance
- Estratégia de buscar primeiro os IDs paginados e depois fazer o `JOIN`

---

##  Algumas consultas usadas na aula

```sql
-- Paginação simples
SELECT * FROM tb_product LIMIT 0,5;
SELECT * FROM tb_product LIMIT 5,5;

-- Join entre produtos e categorias
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id;

-- Join com paginação (não recomendado)
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id
LIMIT 0,5;

-- Consulta por IDs
SELECT * FROM tb_product WHERE id IN (1,2,3,4,5);

-- Join com IN (boa prática)
SELECT * FROM tb_product 
INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
INNER JOIN tb_category ON tb_category.id = tb_product_category.category_id
WHERE tb_product.id IN (1,2,3,4,5);
