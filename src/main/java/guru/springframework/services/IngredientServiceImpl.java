package guru.springframework.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepository ;

	

	public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient,
			IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository,
			UnitOfMeasureRepository uomRepository) {
		super();
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndId(Long valueOf, Long valueOf2) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(valueOf);
		if (!recipeOptional.isPresent()) {
			log.debug("recuip-e not ofount");

		}
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(valueOf2))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

		Optional<Recipe>recipeOptional=recipeRepository.findById(ingredientCommand.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			return new IngredientCommand();
		}else {
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient>ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient->ingredient.getId().equals(ingredientCommand.getId()))
					.findFirst();
			if(ingredientOptional.isPresent()) {
				Ingredient found = ingredientOptional.get();
				found.setDescription(ingredientCommand.getDescription());
				found.setAmount(ingredientCommand.getAmount());
				found.setUom(uomRepository.findById(ingredientCommand.getUom().getId())
											.orElseThrow(()->new RuntimeException("uom not found")));
			}else {
				recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
			}
			Recipe savedRecipe = recipeRepository.save(recipe);
			return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
									.filter(recipeIngredient->recipeIngredient.getId().equals(ingredientCommand.getId()))
									.findFirst()
									.get());
		}
	}

}
