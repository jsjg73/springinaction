package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{

    private final JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        Long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        saveIngredientsToTaco(taco);
        return taco;
    }

    private Long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        String sql = "insert into taco (name, createdAt) values (?, ?)";

        PreparedStatementCreator psc =
            new PreparedStatementCreatorFactory(
                sql, Types.VARCHAR, Types.TIMESTAMP
            ).newPreparedStatementCreator(
                Arrays.asList(
                    taco.getName(),
                    new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientsToTaco(Taco taco) {
        for(Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, taco.getId());
        }
    }

    private void saveIngredientToTaco(Ingredient ingredient, Long id) {
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                id, ingredient.getId()
        );
    }
}
