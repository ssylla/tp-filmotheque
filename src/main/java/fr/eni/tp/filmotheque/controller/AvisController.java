package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Membre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/avis")
@SessionAttributes({"membreEnSession"})
public class AvisController {

    private final FilmService filmService;

    @Autowired
    public AvisController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/creer")
    public String creerAvis(@ModelAttribute("membreEnSession") Membre membre, @RequestParam String idFilm, Model model) {
        if (null != membre && membre.getId() > 0) {
            model.addAttribute("film", filmService.consulterFilmParId(Long.parseLong(idFilm)));
            model.addAttribute("avis", new Avis());
            return "view-film-avis";
        } else {
            return "redirect:/films";
        }
    }

    @PostMapping("/creer")
    public String creerAvis(@ModelAttribute("membreEnSession") Membre membre, @RequestParam String idFilm, @ModelAttribute("avis") Avis avis) {
        if (null != membre && membre.getId() > 0) {
            avis.setMembre(membre);
            filmService.publierAvis(avis, Long.parseLong(idFilm));
        }
        return "redirect:/films";
    }
}
