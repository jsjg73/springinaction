package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	private final TacoRepository tacoRepository;

	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {
//		List<Ingredient> ingredients = Arrays.asList(
//			new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//			new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//			new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//			new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//			new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//			new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//			new Ingredient("CHED", "Cheddar", Type.CHEESE),
//			new Ingredient("JACK", "Monterrey", Type.CHEESE),
//			new Ingredient("SLSA", "Salsa", Type.SAUCE),
//			new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//		);

		List<Ingredient> ingredients = StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
				 									.collect(toList());

		Map<String, List<Ingredient>> ingredientsByTypeName =
				ingredients.stream()
				           .collect(groupingBy(Ingredient::getLowerCaseTypeName));

		model.addAllAttributes(ingredientsByTypeName);
		model.addAttribute("taco", new Taco());
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
		if (errors.hasErrors()) {
			return "design";
		}

		Taco saved = tacoRepository.save(design);
		order.addDesign(saved);

		return "redirect:/orders/current";
	}
}
