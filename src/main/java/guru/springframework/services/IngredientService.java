package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

	
	public IngredientCommand findByRecipeIdAndId(Long valueOf, Long valueOf2);
	public void deleteByRecipeIdAndId(Long valueOf, Long valueOf2);

	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
