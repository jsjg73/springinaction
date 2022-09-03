package tacos.data;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.Ingredient;
import tacos.Taco;

import java.util.Date;

@SpringBootTest
class JdbcTacoRepositoryTest {
    @Autowired
    private JdbcTacoRepository jdbcTacoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void save(){
        Taco taco = new Taco();
        taco.setName("name");
        taco.setCreatedAt(new Date());
        Ingredient flto = ingredientRepository.findById("FLTO");
        taco.setIngredients(Lists.newArrayList(flto));

        Taco save = jdbcTacoRepository.save(taco);

        Assertions.assertNotNull(save);

    }
}