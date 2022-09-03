package tacos.data;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tacos.Ingredient;
import tacos.Taco;

import java.util.Date;

@DataJpaTest
class JdbcTacoRepositoryTest {
    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void save(){
        Taco taco = new Taco();
        taco.setName("name");
        taco.setCreatedAt(new Date());
        Ingredient flto = ingredientRepository.findById("FLTO").get();
        taco.setIngredients(Lists.newArrayList(flto));

        Taco save = tacoRepository.save(taco);

        Assertions.assertNotNull(save);

    }
}