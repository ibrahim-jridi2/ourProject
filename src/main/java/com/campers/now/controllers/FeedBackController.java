package com.campers.now.controllers;

import com.campers.now.models.FeedBack;
import com.campers.now.services.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Tag(name = "Feedback Management")
@RestController
@AllArgsConstructor
@RequestMapping("feedback")
public class FeedBackController {
    private final FeedBackService feedBackService;

    @PostMapping
    public FeedBack add(@RequestBody FeedBack feedBack) {
        return feedBackService.add(feedBack);
    }

    @PutMapping("/{id}")
    public FeedBack update(@RequestBody FeedBack feedBack, @PathVariable("id") Integer id) {
        feedBack.setId(id);
        return feedBackService.update(feedBack);
    }

    @GetMapping("/{id}")
    public FeedBack getOne(@PathVariable("id") Integer id) {
        return feedBackService.getById(id);
    }

    @GetMapping
    public List<FeedBack> getAll(Integer page, String sort, String dir) {

        dir = dir.toUpperCase(Locale.ROOT);
        Sort.Direction sortDir = Sort.Direction.fromString(StringUtils.hasText(dir) ? dir.toUpperCase() : Sort.Direction.ASC.name());
        return feedBackService.getAll(page, sort, sortDir);
    }

}
