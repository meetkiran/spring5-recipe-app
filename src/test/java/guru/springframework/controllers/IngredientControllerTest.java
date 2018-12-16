package guru.springframework.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;

public class IngredientControllerTest {

	@Mock
	IngredientService ingredientService;
	
	@Mock
	RecipeService recipeService;
	@Mock
	UnitOfMeasureService unitOfMeasureService ;
	
	IngredientController 	ingredientController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		this.ingredientController=new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc=MockMvcBuilders.standaloneSetup(ingredientController).build();
	}
	@Test
	public void testListIngredient() throws Exception{
		RecipeCommand recipeCommand = new RecipeCommand ();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("recipe/ingredient/list"))
			   .andExpect(model().attributeExists("recipe"));
		
		verify(recipeService,times(1)).findCommandById(anyLong());
		
	}
	@Test
	public void testUpdateIngredientForm() throws Exception{
		IngredientCommand recipeCommand = new IngredientCommand ();
		when(ingredientService.findByRecipeIdAndId(anyLong(), anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		mockMvc.perform(get("/recipe/1/ingredients/2/update"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("recipe/ingredient/ingredientform"))
			   .andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));
		
		
	}
	
}
