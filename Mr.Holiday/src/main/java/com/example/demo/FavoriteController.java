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
public class FavoriteController {
	@Autowired
	FavoriteRepository repository;
	
	@RequestMapping("/favorite")
	public ModelAndView index(ModelAndView mv) {
		List<FavoriteEntity> list = repository.findAll();
		mv.addObject("list", list);
		mv.setViewName("favorite");
		return mv;
	}

	@RequestMapping("/favorite write")
	public ModelAndView write(ModelAndView mv) {
		mv.addObject("title", "書き込み");
		mv.addObject("form", new FavoriteEntity());
		mv.setViewName("favorite write");
		return mv;
	}
	

	@RequestMapping("/favorite insert")
	@Transactional(readOnly = false)
	public ModelAndView insert(@ModelAttribute("form") @Validated FavoriteEntity entity,
														BindingResult result, ModelAndView mv) {
		if(result.hasErrors()) {
			mv.setViewName("favorite write");
			mv.addObject("title",entity.getId()==null? "書き込み":"更新");
			return mv;
		}
		
		repository.saveAndFlush(entity);
		return new ModelAndView("redirect:/favorite");
	}
	
	@RequestMapping("/favorite data/{id}")
	public ModelAndView data(
			@PathVariable Integer id,ModelAndView mv) {
		return set(mv, id,"詳細情報","favorite data");
	}
	private ModelAndView set(ModelAndView mv,int id,String title,String template) {
		Optional<FavoriteEntity>data=repository.findById(id);
		if(!data.isPresent()) {
			return new ModelAndView("redirect:/favorite");
			}
		mv.addObject("form",data.get());
		mv.addObject("title",title);
		mv.setViewName(template);
		return mv;
	}
	
	@RequestMapping("/favorite update/{id}")
	public ModelAndView update(
			@PathVariable Integer id,ModelAndView mv) {
		return set(mv,id,"更新","favorite write");
		}
	
	@RequestMapping("/favorite delete/{id}")
	public ModelAndView delete(@PathVariable Integer id, ModelAndView mv) {
		return set(mv,id,"削除確認","favorite data");
	}
	
	@RequestMapping("/favorite delete")
	@Transactional(readOnly=false)
	public ModelAndView remove(@RequestParam("id")Integer id) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/favorite");
	}
	
	@RequestMapping("/favorite search")
	public ModelAndView search(
			@RequestParam("key")String key,ModelAndView mv) {
		mv.setViewName("favorite");
		List<FavoriteEntity>list=
				repository.findByGenreLikeOrMoodLike("%"+key+"%","%"+key+"%");
		mv.addObject("list",list);
		return mv;
	}
		
}
