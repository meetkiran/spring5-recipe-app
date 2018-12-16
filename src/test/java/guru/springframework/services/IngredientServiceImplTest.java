package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import java.util.Optional;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class IngredientServiceImplTest {
	 @Mock
	IngredientService ingredientService ;
	 @Mock
	RecipeRepository recipeRepository ;
	 @Mock
	IngredientToIngredientCommand ingredientToIngredientCommand ;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		//this.ingredientService=new IngredientServiceImpl(ingredientToIngredientCommand,recipeRepository);
	}

	@Test
	public void findByRecipeIdAndId() throws Exception{

		Recipe recipe =new Recipe();
		recipe.setId(1l);
		Ingredient ingredient=new Ingredient();
		ingredient.setId(2l);
		recipe.addIngredient(ingredient);
		Optional<Recipe>recipeOptional=Optional.of(recipe);
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		//then
		IngredientCommand ingredientCommand=this.ingredientService.findByRecipeIdAndId(1l,2l);
		assertEquals(Long.valueOf(2l), ingredientCommand.getId());
	}

}
