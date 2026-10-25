package com.scaler.bloggapp.comments;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article/{article-slug}/comments")
public class CommentsController {
}
