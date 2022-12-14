package com.example.controller;


import com.example.model.Music;
import com.example.dto.MusicDto;
import com.example.service.IMusicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MusicController {

    @Autowired
    private IMusicService musicService;

    @GetMapping("/")
    public String showList(Model model) {
        List<Music> musicList = musicService.findAll();
        model.addAttribute("musicList", musicList);
        return "list";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("musicDto", new MusicDto());
        return "create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute @Validated MusicDto musicDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            return "create";
        } else {
            Music music = new Music();
            BeanUtils.copyProperties(musicDto, music);
            musicService.save(music);
            redirectAttributes.addFlashAttribute("mess", "add new successful!");
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable int id, Model model) {
        model.addAttribute("musicList", musicService.findById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("musicList") @Validated MusicDto musicDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            return "edit";
        } else {
            Music music = new Music();
            BeanUtils.copyProperties(musicDto, music);
            musicService.update(music);
            redirectAttributes.addFlashAttribute("mess", "add new successful!");
            return "redirect:/";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("musicList", musicService.findById(id));
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(Music music, RedirectAttributes redirectAttributes) {
        musicService.remove(music);
        redirectAttributes.addFlashAttribute("mess", "Removed customer successfully!");
        return "redirect:/";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("musicList", musicService.findById(id));
        return "view";
    }

    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        model.addAttribute("musicList", musicService.findName(name));
        return "list";
    }
}
