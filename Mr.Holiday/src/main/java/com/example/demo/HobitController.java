package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HobitController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@Autowired
	HobitRepository repository;

	@RequestMapping("/hobit")
	public ModelAndView index(ModelAndView mv) {
		List<HobitEntity> list = repository.findAll();
		mv.addObject("list", list);
		mv.setViewName("hobit");
		return mv;
	}

	@RequestMapping("/write")
	public ModelAndView write(ModelAndView mv) {
		mv.addObject("title", "書き込み");
		mv.addObject("form", new HobitEntity());
		mv.setViewName("write");
		return mv;
	}

	@RequestMapping("/insert")
	@Transactional(readOnly = false)
	public ModelAndView insert(@ModelAttribute("form") @Validated HobitEntity entity,
														BindingResult result, ModelAndView mv) {
		if(result.hasErrors()) {
			mv.setViewName("write");
			mv.addObject("title",entity.getId()==null? "書き込み":"更新");
			return mv;
		}
		repository.saveAndFlush(entity);
		return new ModelAndView("redirect:/hobit");
	}
	
	@RequestMapping("/update/{id}")
	public ModelAndView update(
			@PathVariable Integer id,ModelAndView mv) {
		return set(mv,id,"更新","write");
		}

	private ModelAndView set(ModelAndView mv, int id, String title, String template) {
		Optional<HobitEntity>update=repository.findById(id);
		if(!update.isPresent()) {
			return new ModelAndView("redirect:/");
		}
		mv.addObject("form",update.get());
		mv.addObject("title",title);
		mv.setViewName(template);
		return mv;
		}
	
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Integer id,ModelAndView mv) {
		return set(mv,id,"削除確認","data");
	}
	
	@RequestMapping("/delete")
	@Transactional(readOnly=false)
	public ModelAndView remove(@RequestParam("id")Integer id) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/hobit");
	}
	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam("key")String key,ModelAndView mv) {
		mv.setViewName("hobit");
		List<HobitEntity> list=
				repository.findByGenreLikeOrMoodLike("%"+key+"%","%"+key+"%");
		mv.addObject("list",list);
		return mv;
	}
}


	

