package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/19/17.
 */
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    
    //private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }
    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

         recipeService.deleteRecipeCommand(new Long(id));
       
        return "redirect:/";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  "recipe/recipeform";
    }
   /* @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id, Model model){
        model.addAttribute("recipe", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId,Long.valueOf(id))));
        model.addAttribute("uomList", uomService.listAllUoms());
        return  "recipe/ingredient/ingredientForm";
    }*/
    

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
