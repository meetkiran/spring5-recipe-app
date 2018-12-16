package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitofMeasureService;
	public IngredientController(RecipeService recipeService,IngredientService ingredientService,UnitOfMeasureService unitofMeasureService ) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitofMeasureService=unitofMeasureService;
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting info from ingreient");
		model.addAttribute("recipe", this.recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,@PathVariable String id, Model model) {
		log.debug("Getting info from ingreient with id");
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId),Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients/{id}/update")
	public String updtateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id, Model model) {
		log.debug("Getting info from ingreient with id");
		model.addAttribute("ingredient", this.ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId),Long.valueOf(id)));
		model.addAttribute("uomList", this.unitofMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}
	
	
	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdateRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
		log.debug("update info from ingreient ");
		IngredientCommand savedIngredient=ingredientService.saveIngredientCommand(ingredientCommand);
		
		return "redirect:/recipe/"+savedIngredient.getRecipeId()+"/ingredient/"+savedIngredient.getId()+"/show";
	}
}
